package com.example.loginbasic

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object NetworkUtils {

    // Hàm gửi request chung (Hỗ trợ GET và POST JSON)
    fun sendRequest(urlString: String, method: String, jsonBody: String? = null): String {
        try {
            val url = URL(urlString)
            val conn = url.openConnection() as HttpURLConnection

            conn.requestMethod = method
            conn.connectTimeout = 10000 // 10 giây timeout
            conn.readTimeout = 10000

            // Cấu hình Header cho PostgREST
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            conn.setRequestProperty("Accept", "application/json")

            // Nếu là POST, ghi dữ liệu body
            if (method == "POST" && jsonBody != null) {
                conn.doOutput = true
                val writer = OutputStreamWriter(conn.outputStream)
                writer.write(jsonBody)
                writer.flush()
                writer.close()
            }

            // Đọc phản hồi
            val responseCode = conn.responseCode
            // Nếu code 2xx thì đọc inputStream, nếu lỗi (4xx, 5xx) thì đọc errorStream
            val stream = if (responseCode in 200..299) conn.inputStream else conn.errorStream

            val reader = BufferedReader(InputStreamReader(stream))
            val response = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
            reader.close()

            return response.toString()

        } catch (e: Exception) {
            e.printStackTrace()
            return "Error: ${e.message}"
        }
    }
}