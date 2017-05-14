package payments.utils.extractors;

import org.apache.log4j.Logger;
import payments.controller.exception.ControllerException;
import payments.utils.constants.MessageKeys;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParamExtractor {
    private static final Logger logger = Logger.getLogger(RequestParamExtractor.class);
    public static final String NUMBER_BETWEEN_SLASHES_PATTERN = "\\d+(?=/|$)";
    private Pattern numberPattern = Pattern.compile(NUMBER_BETWEEN_SLASHES_PATTERN);

    public long extractLong(HttpServletRequest request, String param){
        try {
            return Long.parseLong(request.getParameter(param));
        } catch (NumberFormatException e) {
            logger.error(MessageKeys.WRONG_QUERY_PARAMETER);
            throw new ControllerException(MessageKeys.WRONG_QUERY_PARAMETER);
        }
    }

    public double extractDouble(HttpServletRequest request, String param){
        try{
            return Double.parseDouble(request.getParameter(param));
        }
        catch (NumberFormatException e){
            logger.error(MessageKeys.WRONG_QUERY_PARAMETER);
            throw new ControllerException(MessageKeys.WRONG_QUERY_PARAMETER);
        }
    }

    public Integer extractPaginParam(HttpServletRequest request, String param) {
        try{
            return Integer.parseInt(request.getParameter(param));
        }
        catch (NumberFormatException e){
        }
        return null;
    }

    public long extractSingleLongPathParam(HttpServletRequest request){
        String uri = request.getRequestURI();
        Matcher matcher = numberPattern.matcher(uri);
        if(matcher.find()) {
            return extractLongFromString(matcher.group());
        }
        return 0;
    }

    public List<Long> extractLongPathParams(HttpServletRequest request) {
        String uri = request.getRequestURI();
        List<Long> params = new ArrayList<>();
        Matcher matcher = numberPattern.matcher(uri);
        while (matcher.find()) {
            long param = extractLongFromString(matcher.group());
            params.add(param);
        }
        return params;
    }

    public Date extractDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = null;
        try {
            convertedDate = sdf.parse(date);
        }
        catch (ParseException ex){}
        return convertedDate;
    }

    private long extractLongFromString(String str){
        long result=0;
        try {
            result = Long.parseLong(str);
        }
        catch (NumberFormatException ex){}
        return result;
    }
}