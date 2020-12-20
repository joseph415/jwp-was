package http.response;

import static http.HttpHeaders.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.ContentType;
import http.HttpVersion;

class HttpResponseTest {
    @DisplayName("새로운 statusLine으로 update가 된다.")
    @Test
    void ShouldUpdateStatusLine() {
        HttpResponse httpResponse = new HttpResponse();

        httpResponse.setStatusLine(StatusLine.from(HttpVersion.VERSION_1_1, HttpStatus.OK));

        assertAll(
                () -> assertThat(httpResponse.getStatusLine().getHttpVersion()).isEqualTo(
                        HttpVersion.VERSION_1_1),
                () -> assertThat(httpResponse.getStatusLine().getHttpStatus()).isEqualTo(
                        HttpStatus.OK)
        );
    }

    @DisplayName("주어진 body가 update 된다.")
    @Test
    void ShouldUpdateBody() {
        String body = "<h1>a</h1>";
        HttpResponse httpResponse = new HttpResponse();

        httpResponse.setBody(body.getBytes(), ContentType.HTML.getContentType());

        assertAll(
                () -> assertThat(httpResponse.getBody()).isEqualTo(body.getBytes()),
                () -> assertThat(
                        httpResponse.getHttpHeaders()
                                .getAttribute(CONTENT_TYPE)).isEqualTo(
                        ContentType.HTML.getContentType())
        );
    }

    @DisplayName("redirect 하도록 response를 구성한다")
    @Test
    void shouldSetResponseToRedirect() {
        HttpResponse httpResponse = new HttpResponse();

        httpResponse.redirect(HttpVersion.VERSION_1_1, "/index.html",
                ContentType.HTML.getContentType());

        assertAll(
                () -> assertThat(httpResponse.getStatusLine().getHttpStatus()).isEqualTo(
                        HttpStatus.MOVED_PERMANENTLY),
                () -> assertThat(httpResponse.getHttpHeaders().getAttribute(LOCATION)).isEqualTo(
                        "/index.html"),
                () -> assertThat(
                        httpResponse.getHttpHeaders()
                                .getAttribute(CONTENT_TYPE)).isEqualTo(
                        ContentType.HTML.getContentType())
        );
    }

    @DisplayName("response를 보내기 위해 OutputStream에 입력한다.")
    @Test
    void ShouldBeWriteDataOutputStream() {
        String body = "<h1>a</h1>";
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setBody(body.getBytes(), ContentType.HTML.getContentType());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //어떻게 스트림에 테스트할지가 관심이 아니고 잘 써져있는지 관심
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        String expect = ""
                + "HTTP/1.1 200 OK\n"
                + "Content-Type: text/html;charset=utf-8\n"
                + "Content-Length: 10\n"
                + "\n"
                + "<h1>a</h1>";

        httpResponse.send(dataOutputStream);

        assertThat(byteArrayOutputStream.toString()).isEqualTo(expect);
    }
}