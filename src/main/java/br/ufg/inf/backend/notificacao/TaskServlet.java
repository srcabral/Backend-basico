package br.ufg.inf.backend.notificacao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.backend.tasks.model.Task;
import br.ufg.inf.backend.tasks.model.TaskPriority;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tarefas")
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String myNewTask;
	private String myNewPriority;

	private TaskService taskService = new TaskService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.getWriter().println(taskService.listar());
		} catch (Exception e) {
			resp.getWriter().print("Algo deu errado");
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int indice;
		taskService = new TaskServiceWithEmailNotification(new EmailNotificacaoService());

		myNewTask = req.getParameter("task");
		myNewPriority = req.getParameter("priority");
		try {
			indice = Integer.parseInt(req.getParameter("index"));
		} catch (NumberFormatException e) {
			throw new RuntimeException("Valor para o indice invalido!");
		}
		
		resp.getWriter().println(taskService.atualizar(myNewTask, myNewPriority, indice));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		taskService = new TaskServiceWithSMSNotification(new SMSNotificacaoService());

		myNewTask = req.getParameter("task");
		myNewPriority = req.getParameter("priority");

		resp.getWriter().println(taskService.adicionar(myNewTask, myNewPriority));
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String myTask = req.getParameter("task");
		
		resp.getWriter().println(taskService.remover(myTask));
	}
}
