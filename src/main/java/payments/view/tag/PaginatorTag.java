package payments.view.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;

/**
 * This class represent custom tag handler, which transforms tag to list page navigator
 */
public class PaginatorTag extends SimpleTagSupport {
    private final static int FIRST_PAGE_NUMBER=1;

    private String paginParamName;
    private String uri;
    private int currentPageNumber;
    private int totalPages;
    private int maxPaginElems =10;

    public void setPaginParamName(String paginParamName) {
        this.paginParamName = paginParamName;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setMaxPaginElems(int maxPaginElems) {
        this.maxPaginElems = maxPaginElems;
    }

    @Override
    public void doTag() throws JspException {
        if(totalPages == 0 || totalPages == FIRST_PAGE_NUMBER){
            return;
        }

        resolveRequestGetParams();

        try {
            JspWriter out = getJspContext().getOut();

            boolean isCurrentPageLast= currentPageNumber == totalPages;
            boolean isCurrentPageFirst = currentPageNumber == FIRST_PAGE_NUMBER;

            int pagingStartNumber = Math.max(currentPageNumber - maxPaginElems / 2, 1);
            int pagingEndNumber = Math.min(pagingStartNumber + maxPaginElems,totalPages);


            printBeginTag(out);

            if(!isCurrentPageFirst){
                printPreviousPageItem(out);
            }
            printPagingElems(out,pagingStartNumber,pagingEndNumber);

            if(!isCurrentPageLast)
                printNextPageItem(out);

            printEndTag(out);
        }
        catch (IOException e){
            throw new JspException("Error in pagination "+e);
        }
    }



    private void printPagingElems(JspWriter out, int pagingStartNumber, int pagingEndNumber) throws IOException {
        for (int i = pagingStartNumber; i <= pagingEndNumber; i++) {

            if (i == currentPageNumber)
                out.write(constructLink(i,String.valueOf(i),"active"));
            else
                out.write(constructLink(i));
        }
    }

    private void printNextPageItem(JspWriter out) throws IOException {
        out.write(constructLink(currentPageNumber + 1, "&raquo;"));

    }

    private void printPreviousPageItem(JspWriter out) throws IOException {
        out.write(constructLink(currentPageNumber - 1, "&laquo;"));
    }

    private void printBeginTag(JspWriter out) throws IOException {
        out.write("<div class='container'>" +
                "<div class=\"col-centered center\">" +
                "<ul class=\"pagination  \">");
    }

    private void printEndTag(JspWriter out) throws IOException {
        out.print("</div></div></ul>");
    }


    private String constructLink(int page) {
        return constructLink(page, String.valueOf(page), null);
    }

    private String constructLink(int page,String text) {
        return constructLink(page, text, null);
    }


    private String constructLink(int page, String text, String className) {
        StringBuilder link = new StringBuilder("<li");

        if (className != null) {
            link.append(" class=\"");
            link.append(className);
            link.append("\"");
        }

        link.append(">")
                .append("<a href="+uri)
                .append(paginParamName.concat("=")+page)
                .append(">")
                .append(text)
                .append("</a></li>");
        return link.toString();
    }

    private void resolveRequestGetParams(){
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        currentPageNumber = Optional.ofNullable(request.getParameter(paginParamName))
                .map(Integer::parseInt).orElse(FIRST_PAGE_NUMBER);

        Enumeration requestGetParams=request.getParameterNames();

        StringBuilder resultUri=new StringBuilder("?");

        while(requestGetParams.hasMoreElements()){
            String paramName=requestGetParams.nextElement().toString();

            if(!paramName.equals(paginParamName)){
                resultUri.append(
                        paramName
                                .concat("=")
                                .concat(request.getParameter(paramName)))
                        .append("&");
            }
        }
        uri=resultUri.toString();
    }
}