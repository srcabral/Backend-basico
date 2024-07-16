package br.ufg.inf.backend.calculator;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calculator")
public class CalculatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String num1 = request.getParameter("num1");
		String num2 = request.getParameter("num2");
		Double total = 0.0;

		try {
			Double number1 = Double.parseDouble(num1);
			Double number2 = Double.parseDouble(num2);
			String operator = request.getParameter("operator");

			switch (operator) {
			case "adicao":
				total = number1 + number2;
				break;
			case "subtracao":
				total = number1 - number2;
				break;
			case "multiplicacao":
				total = number1 * number2;
				break;
			case "divisao":
				total = number1 / number2;
				break;
			default:
				response.getWriter().println("Erro na operacao! Informe entre as seguintes operacoes: "
						+ "adicao, "
						+ "subtracao, "
						+ "multiplicacao, "
						+ "divisao");
				return;
			}

			response.getWriter().append(resultado(operator, total));

		} catch (NumberFormatException e) {
			if (num1.isEmpty()) {
				response.getWriter().append("O valor para o numero 1 eh invalido!");
			} else if (num2.isEmpty()) {
				response.getWriter().append("O valor para o numero 2 eh invalido!");
			} else {
				response.getWriter().append("Um dos valores informado nao eh um numero");
			}
		}
	}

	private String resultado(String operacao, Double total) {
		return String.format("O total da %s eh: %.2f", operacao, total);
	}
}
