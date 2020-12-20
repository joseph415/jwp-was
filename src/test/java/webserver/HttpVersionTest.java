package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import http.HttpVersion;

class HttpVersionTest {
    @DisplayName("HTTP VERSION에 해당하는 객체 반환")
    @ParameterizedTest
    @CsvSource(value = {"HTTP/1.0,VERSION_1_0", "HTTP/1.1,VERSION_1_1", "HTTP/2.0,VERSION_2_0",
            "HTTP/3.0,VERSION_3_0"})
    void from(String input, HttpVersion version) {
        assertThat(HttpVersion.from(input)).isEqualTo(version);
    }

    @DisplayName("HTTP VERSION이 존재하지 않는 경우 UNKNOWN 객체 반환")
    @Test
    void from_Unknown() {
        assertThat(HttpVersion.from("HELLO")).isEqualTo(HttpVersion.UNKNOWN);
    }
}
