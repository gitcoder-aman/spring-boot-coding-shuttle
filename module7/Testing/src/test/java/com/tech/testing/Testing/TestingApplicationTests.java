package com.tech.testing.Testing;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

//@SpringBootTest
@Slf4j
class TestingApplicationTests {


	@BeforeEach  // indicate that the annotated method should be executed before each test method.
		// These can be used to reset each test case conditions.
	void setup(){
		log.info("stating the method , setting up config");
	}

	@AfterEach
		// indicate that the annotated method should be executed after each test method.
		// These can be used to reset each test case conditions.
	void tearDown(){
		log.info("Tearing down the method , setting up config");
	}

	@BeforeAll
	static void setUpOnce(){
		log.info("Setup Once:");
	}

	@AfterAll
	static void tearDownOnce(){
		log.info("Tear Down all....:");
	}
	@Test
//	@Disabled
	void testNumberOne() {
		log.info("test one is run");

		int a = 5;
		int b = 7;

		int result = addTwoNumber(a,b);
//		Assertions.assertEquals(12, result);

		Assertions.assertThat(result).isEqualTo(12).isCloseTo(11, Offset.offset(1));

		Assertions.assertThat("Apple")
				.isEqualTo("Apple")
				.startsWith("App")
				.endsWith("le")
				.hasSize(5);

	}

	@Test
//	@DisplayName("displayTestNameTwo")
	void testNumberTwo(){
		log.info("test two is run");
	}


	int addTwoNumber(int a,int b){
		return a+b;
	}

	@Test
	void testDivideTwoNumbers_whenDenominatorIsZero_ThenArithmeticException(){
		int a = 5;
		int b = 0;

		Assertions.assertThatThrownBy(()->divideTwoNumbers(a,b))
				.isInstanceOf(ArithmeticException.class)
				.hasMessage("Tired to divide by zero");
	}

	double divideTwoNumbers(int a,int b){
		try{
			return a/b;
		}catch (ArithmeticException e){
            log.error("Arithmetic Exception Occurred: {}", e.getLocalizedMessage());
			throw new ArithmeticException("Tired to divide by zero");
		}
	}

}
