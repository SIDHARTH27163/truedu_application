package com.example.true_edu_application;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SoapRequestAsyncTasksin extends AsyncTask<Void, Void, String> {

    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://trueedu.appilogics.in/WebService.asmx";
    private static final String METHOD_NAME = "getpackage_bycoursesem";

    private String course;
    private String sem;

    public SoapRequestAsyncTasksin(String course, String sem) {
        this.course = course;
        this.sem = sem;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String response = "";

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            // Add parameters to the request
            PropertyInfo courseInfo = new PropertyInfo();
            courseInfo.setName("course");
            courseInfo.setValue(course);
            courseInfo.setType(String.class);
            request.addProperty(courseInfo);

            PropertyInfo semInfo = new PropertyInfo();
            semInfo.setName("sem");
            semInfo.setValue(sem);
            semInfo.setType(String.class);
            request.addProperty(semInfo);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(NAMESPACE + METHOD_NAME, envelope);

            Object responseObj = envelope.getResponse();
            if (responseObj instanceof SoapPrimitive) {
                SoapPrimitive soapPrimitive = (SoapPrimitive) responseObj;
                response = soapPrimitive.toString();
            } else if (responseObj instanceof SoapObject) {
                // Handle the specific structure of your response
                SoapObject soapObject = (SoapObject) responseObj;

                // Get the "getpackage_bycoursesemResult" property
                Object resultObj = soapObject.getProperty("getpackage_bycoursesemResult");
                if (resultObj instanceof SoapPrimitive) {
                    SoapPrimitive resultPrimitive = (SoapPrimitive) resultObj;
                    response = resultPrimitive.toString();
                } else {
                    response = resultObj.toString();
                }
            }
            Log.d("res", response);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("exception", e.getMessage());
        }

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            // Update the UI directly, for example, set a TextView's text
//            TextView textView = textView.findViewById(); // Replace with your TextView's ID
//            textView.setText(result);
            Log.d("result responser", result);
        } else {
            Log.d("SOAP Response", "Response is null"); // Log a message to indicate a null response
        }
    }
}
