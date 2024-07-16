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
	private TaskServiceWithEmailNotification taskWithEmailNotification = new  TaskServiceWithEmailNotification(new EmailNotificacaoService());
	private TaskServiceWithSMSNotification taskWithSMSNotification = new TaskServiceWithSMSNotification(new SMSNotificacaoService());

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.getWriter().println(taskService.listar());
		} catch(Exception e) {
			resp.getWriter().print("Algo deu errado");
		}
	}

//	@Override
//	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		int indice;
//
//		myNewTask = req.getParameter("task");
//		myNewPriority = req.getParameter("priority");
//		try {
//			indice = Integer.parseInt(req.getParameter("index"));
//		} catch (NumberFormatException e) {
//			throw new RuntimeException("Valor para o indice invalido!");
//		}
//
//		if (listIsEmpty(resp)) {
//			return;
//		} else {
//
//			for (Task task : tasksList) {
//				if (tasksList.indexOf(task) == indice - 1) {
//					task.setTaskName(myNewTask);
//					task.setTaskPriority(searchPriority(myNewPriority));
//					resp.getWriter().append("Tarefa atualizada com sucesso!!");
//					return;
//				}
//			}
//
//			resp.getWriter().append(String.format("Tarefa nao encontrada. Sua lista possui apenas %d tarefa(s) cadastrada(s)", tasksList.size()));
//		}
//
//	}
//
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		taskService = taskWithSMSNotification;
		
		myNewTask = req.getParameter("task");
		myNewPriority = req.getParameter("priority");
		
		resp.getWriter().println(taskService.adicionar(myNewTask, myNewPriority));
	}
//
//	@Override
//	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		myNewTask = req.getParameter("task");
//
//		if (listIsEmpty(resp)) {
//			return;
//		} else {
//			for (Task task : tasksList) {
//				if (task.getTaskName().equals(myNewTask)) {
//					tasksList.remove(tasksList.indexOf(task));
//					resp.getWriter().append(String.format("Tarefa %s removida com sucesso!", myNewTask));
//				} else {
//					resp.getWriter().append("Voce nao possui essa tarefa na sua lista! Nao eh possivel remover");
//				}
//			}
//		}
//	}
//
//	private Boolean listIsEmpty(HttpServletResponse resp) throws IOException {
//		if (tasksList.isEmpty()) {
//			resp.getWriter().append("Sua lista de tarefas estah vazia!");
//			return true;
//		}
//		return false;
//	}
//
	
}
