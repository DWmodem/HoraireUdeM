package com.mobile.umontreal.schedule.parsing;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Corneliu on 13-Mar-2015.
 */
enum DownloadStatus { IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK }

public class ReadData {

        private String LOG_TAG = ReadData.class.getSimpleName();

        private String url;
        private String data;
        private DownloadStatus downloadStatus;

        //When reading data, set url
        public ReadData(String url) {
            this.url = url;
            downloadStatus = DownloadStatus.IDLE;
        }

        public void Reset() {
            downloadStatus = DownloadStatus.IDLE;
            data = null;
            url = null;
        }

        public String getData() {
            return data;
        }

        public DownloadStatus getDownloadStatus() {
            return downloadStatus;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void execute() {
            this.downloadStatus = DownloadStatus.PROCESSING;
            DownloadData downloadData = new DownloadData();
            downloadData.execute(url);
        }

        //Download as async to free up the interface for better usability
        public class DownloadData extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String webData) {

                data = webData;

                if (data == null) {
                    if (url == null) {
                        downloadStatus = DownloadStatus.NOT_INITIALISED;
                    } else {
                        downloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                    }
                } else {
                    // success
                    downloadStatus = DownloadStatus.OK;
                }
            }

            protected String doInBackground(String... params) {

                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                if (params == null)
                    return null;

                try {

                    URL url = new URL(params[0]);
                    Log.v(LOG_TAG, "URL Path : " + params[0]);

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    if (inputStream == null) {
                        return null;
                    }

                    StringBuffer buffer = new StringBuffer();
                    reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                    //Clean the json from formatting artifacts
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line.replaceAll("&#039;", "'");
                        line = line.replaceAll("<br />", "\n");
                        line = line.replaceAll("&nbsp;", " ");
                        buffer.append(line + "\n");
                    }

                    return buffer.toString();

                } catch (IOException e){
                    Log.e(LOG_TAG, "Error e: ", e);
                    return null;

                } finally {

                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }

                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e(LOG_TAG, "Error closing stream", e);
                        }
                    }
                }
            }
        }
    }