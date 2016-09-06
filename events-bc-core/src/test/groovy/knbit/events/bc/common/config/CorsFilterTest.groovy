package knbit.events.bc.common.config

import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

import javax.servlet.FilterChain

/**
 * Created by novy on 29.07.15.
 */
class CorsFilterTest extends Specification {

    def "given allowed domain with random port, it should set Access-Control-Allow-Origin as taken from request"() {
        given:
        def objectUnderTest = new CorsFilter()

        when:
        def request = new MockHttpServletRequest()
        request.addHeader("Origin", "http://" + allowedDomain + ":6666")
        def response = new MockHttpServletResponse()
        objectUnderTest.doFilter(request, response, Mock(FilterChain))

        then:
        response.getHeader("Access-Control-Allow-Origin") == "http://" + allowedDomain + ":6666"

        where:
        allowedDomain << CorsFilter.ALLOWED_DOMAINS
    }

    def "if domain not allowed, it should set first allowed domain"() {
        given:
        def objectUnderTest = new CorsFilter()

        when:
        def request = new MockHttpServletRequest()
        request.addHeader("Origin", "http://example.com")
        def response = new MockHttpServletResponse()
        objectUnderTest.doFilter(request, response, Mock(FilterChain))

        then:
        def firstAllowedDomain = CorsFilter.ALLOWED_DOMAINS.first()
        response.getHeader("Access-Control-Allow-Origin") == "http://" + firstAllowedDomain
    }

    def "given invalid url, it should set first allowed domain"() {
        given:
        def objectUnderTest = new CorsFilter()

        when:
        def request = new MockHttpServletRequest()
        request.addHeader("Origin", "this is not fucking url")
        def response = new MockHttpServletResponse()
        objectUnderTest.doFilter(request, response, Mock(FilterChain))

        then:
        def firstAllowedDomain = CorsFilter.ALLOWED_DOMAINS.first()
        response.getHeader("Access-Control-Allow-Origin") == "http://" + firstAllowedDomain
    }
}
