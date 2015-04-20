package ca.easyevent.utils;

import android.content.Context;

import org.apache.http.NameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class HttpCustomRequest {


	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private String mUri;
	private String mMethod="GET";
	private List<NameValuePair> mParameters;
	private List<NameValuePair> mHeaders;
	private String mBody;
	private Context mContext;


	/*##############################################################################################
								Constructeur
	###############################################################################################*/

    public HttpCustomRequest(Context inContext,String inUrl){
		this.setContext(inContext);
		this.mUri = inUrl;
		this.mParameters = new ArrayList<NameValuePair>();
		this.mHeaders = new ArrayList<NameValuePair>();
		this.mBody = null;
	}

    /*##############################################################################################
                                ACCESSEURS
    ###############################################################################################*/

	public byte[] getBodyEncoded(){
		byte[] encodedValue=null;
		if(this.mBody==null)
            return encodedValue;
		try {
			encodedValue= this.mBody.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodedValue;
	}


    public String getMethod(){
        return this.mMethod;
    }


    public URL getURL() throws MalformedURLException{
        if(this.mParameters.size()>0){
            try {
                return new URL(this.mUri+"?"+this.getQueryParmeters());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else return new URL(this.mUri);
        return new URL(this.mUri);
    }

    public List<NameValuePair> getHeaders(){
        return this.mHeaders;
    }

    private String getQueryParmeters() throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : this.mParameters)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public Context getContext() {
        return mContext;
    }


    /*##############################################################################################
                            MODIFICATEUR
    ###############################################################################################*/

    public void setPairValue(List<NameValuePair> content){
		this.mParameters = content;
	}

    public void setBody(String inBody){
        this.mBody = inBody;
    }

	public void setMethod(String inMethod) { 
		this.mMethod = inMethod;
		}

	public void setContext(Context mContext) {
		this.mContext = mContext;
	}

}
