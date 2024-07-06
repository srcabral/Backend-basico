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
		Double total = 0.0;

		try {
			Double number1 = Double.parseDouble(request.getParameter("num1"));
			Double number2 = Double.parseDouble(request.getParameter("num2"));
			String operator = request.getParameter("operator");

			switch (operator) {
			case "adicao":
				total = number1 + number2;
//				response.getWriter().println(String.format("O total da soma eh: %s", total));
				break;
			case "subtracao":
				total = number1 - number2;
//				response.getWriter().println(String.format("O total da subtracao eh: %s", total));
				break;
			case "multiplicacao":
				total = number1 * number2;
//				response.getWriter().println(String.format("O total da multiplicacao eh: %s", total));
				break;
			case "divisao":
				total = number1 / number2;
//				response.getWriter().println(String.format("O total da divisao eh: %s", total));
				break;
			default:
				response.getWriter().println("Erro na operacao");
				return;
			}

			response.getWriter().append(resultado(operator, total));

		} catch (NullPointerException e) {
			response.getWriter().append("Valor passado está vazio!!");
		} catch (NumberFormatException e) {
			response.getWriter().append("passei aqui");
		}
	}

	private String resultado(String operacao, Double total) {
		return String.format("O total da %s eh: %.2f", operacao, total);
	}
}
