package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestHandlerTest {

    @DisplayName("uri에 슬래시(/)만 입력해도 /index.html 경로로 설정한다")
    @Test
    void uri에_슬래시만_입력해도_index_html로_설정() throws IOException {
        // given
        final String requestHeader = String.join("\r\n",
                "GET / HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        Socket socket = mock(Socket.class);

        // when
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(requestHeader.getBytes()));
        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        RequestHandler requestHandler = new RequestHandler(socket);
        requestHandler.run();

        // then

    }

//  GET /js/jquery-2.2.0.min.js HTTP/1.1
//  Host: localhost:8080
//  User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:68.0) Gecko/20100101 Firefox/68.0
//  Accept: */*
//  Accept-Language: ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3
//  Accept-Encoding: gzip, deflate
//  Connection: keep-alive
//  Referer: http://localhost:8080/
//  Cookie: x-session=v+I5u1bLtPVKeHpYnWWbob95o3TZ444UBQ4nbNwDHCZkszndRY289Y6CrGPjPo2Nlj4ahjBLLGrFiEpyz/uMAQ==; x-session-lv=10; x-session-id=hkkang; _ga=GA1.1.209105394.1527213195; Idea-52801c69=df9d0df4-cff9-4880-93ec-910726ad2cf9; Idea-52802028=74b01eb9-d605-4d67-8d91-9a0c182c9e13; jwt=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJmbG93MTA3NEBnbWFpbC5jb20iLCJuYW1lIjoiQWRtaW5pc3RyYXRvciIsInBpY3R1cmVVcmwiOm51bGwsInRpbWV6b25lIjoiQW1lcmljYS9OZXdfWW9yayIsImxvY2FsZUNvZGUiOiJlbiIsImRlZmF1bHRFZGl0b3IiOiJtYXJrZG93biIsInBlcm1pc3Npb25zIjpbIm1hbmFnZTpzeXN0ZW0iXSwiZ3JvdXBzIjpbMV0sImlhdCI6MTU1OTEyMDA3NywiZXhwIjoxNTU5MTIxODc3LCJhdWQiOiJ1cm46d2lraS5qcyIsImlzcyI6InVybjp3aWtpLmpzIn0.cmA0R1DEnT2qCWxAaDPkgNbCipnjvJZD0keJseJT4wFmPSl9NPI3s5NF9LqdOMlRFa9OLNwuGZFowR4WA3aPTxgtnfngmQcxq_jkYE-pJ9BR5wCO_pumsBc_ZzkLf8BDfI7va8KfG3_avsf6yKZ7z4TylsE1uCLVmFeA1kFjvvjrAsT9JwlzYbLffJS_CR8ix0paW1CK4ZOe5j9aUKJnQ-ZoBnE9tAq-8LXDuv-vVVixn8ri26P_VIxp18sWXZPfhRUz6Qv-6frqgPveu43qlOo6rv71_v3PcmF9-2kLfamOXPMA4FSiAF9i7wrjcVU5dhNKD8atMYmISbnJrZn18A; Idea-528023e9=74b01eb9-d605-4d67-8d91-9a0c182c9e13; hibext_instdsigdipv2=1
//  Pragma: no-cache
//  Cache-Control: no-cache

//  GET /css/styles.css HTTP/1.1
//  Host: localhost:8080
//  User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:68.0) Gecko/20100101 Firefox/68.0
//  Accept: text/css,*/*;q=0.1
//  Accept-Language: ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3
//  Accept-Encoding: gzip, deflate
//  Connection: keep-alive
//  Referer: http://localhost:8080/
//  Cookie: x-session=v+I5u1bLtPVKeHpYnWWbob95o3TZ444UBQ4nbNwDHCZkszndRY289Y6CrGPjPo2Nlj4ahjBLLGrFiEpyz/uMAQ==; x-session-lv=10; x-session-id=hkkang; _ga=GA1.1.209105394.1527213195; Idea-52801c69=df9d0df4-cff9-4880-93ec-910726ad2cf9; Idea-52802028=74b01eb9-d605-4d67-8d91-9a0c182c9e13; jwt=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJmbG93MTA3NEBnbWFpbC5jb20iLCJuYW1lIjoiQWRtaW5pc3RyYXRvciIsInBpY3R1cmVVcmwiOm51bGwsInRpbWV6b25lIjoiQW1lcmljYS9OZXdfWW9yayIsImxvY2FsZUNvZGUiOiJlbiIsImRlZmF1bHRFZGl0b3IiOiJtYXJrZG93biIsInBlcm1pc3Npb25zIjpbIm1hbmFnZTpzeXN0ZW0iXSwiZ3JvdXBzIjpbMV0sImlhdCI6MTU1OTEyMDA3NywiZXhwIjoxNTU5MTIxODc3LCJhdWQiOiJ1cm46d2lraS5qcyIsImlzcyI6InVybjp3aWtpLmpzIn0.cmA0R1DEnT2qCWxAaDPkgNbCipnjvJZD0keJseJT4wFmPSl9NPI3s5NF9LqdOMlRFa9OLNwuGZFowR4WA3aPTxgtnfngmQcxq_jkYE-pJ9BR5wCO_pumsBc_ZzkLf8BDfI7va8KfG3_avsf6yKZ7z4TylsE1uCLVmFeA1kFjvvjrAsT9JwlzYbLffJS_CR8ix0paW1CK4ZOe5j9aUKJnQ-ZoBnE9tAq-8LXDuv-vVVixn8ri26P_VIxp18sWXZPfhRUz6Qv-6frqgPveu43qlOo6rv71_v3PcmF9-2kLfamOXPMA4FSiAF9i7wrjcVU5dhNKD8atMYmISbnJrZn18A; Idea-528023e9=74b01eb9-d605-4d67-8d91-9a0c182c9e13; hibext_instdsigdipv2=1
//  Pragma: no-cache
//  Cache-Control: no-cache

}