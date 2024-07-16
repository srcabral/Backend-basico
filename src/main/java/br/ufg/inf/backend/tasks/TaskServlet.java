package br.ufg.inf.backend.tasks;

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

@WebServlet("/task")
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String myNewTask;
	private String myNewPriority;

	private List<Task> tasksList;

	public TaskServlet() {
		this.tasksList = new ArrayList<>();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (listIsEmpty(resp)) {
			return;
		} else {
			for (Task task : tasksList) {
				resp.getWriter().println(tasksList.indexOf(task) + 1 + ": " + task.getTaskName() + " - Prioridade: "
						+ task.getTaskPriority());
			}
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int indice;

		myNewTask = req.getParameter("task");
		myNewPriority = req.getParameter("priority");
		try {
			indice = Integer.parseInt(req.getParameter("index"));
		} catch (NumberFormatException e) {
			throw new RuntimeException("Valor para o indice invalido!");
		}

		if (listIsEmpty(resp)) {
			return;
		} else {

			for (Task task : tasksList) {
				if (tasksList.indexOf(task) == indice - 1) {
					task.setTaskName(myNewTask);
					task.setTaskPriority(searchPriority(myNewPriority));
					resp.getWriter().append("Tarefa atualizada com sucesso!!");
					return;
				}
			}

			resp.getWriter().append(String.format("Tarefa nao encontrada. Sua lista possui apenas %d tarefa(s) cadastrada(s)", tasksList.size()));
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		myNewTask = req.getParameter("task");
		myNewPriority = req.getParameter("priority");

		for (Task task : tasksList) {
			if (task.getTaskName().equals(myNewTask)) {
				resp.getWriter().append("Estah tarefa ja existe!");
				return;
			}
		}

		tasksList.add(new Task(myNewTask, searchPriority(myNewPriority)));
		resp.getWriter().append(String.format("Tarefa %s adicionada!!", myNewTask));
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		myNewTask = req.getParameter("task");

		if (listIsEmpty(resp)) {
			return;
		} else {
			for (Task task : tasksList) {
				if (task.getTaskName().equals(myNewTask)) {
					tasksList.remove(tasksList.indexOf(task));
					resp.getWriter().append(String.format("Tarefa %s removida com sucesso!", myNewTask));
				} else {
					resp.getWriter().append("Voce nao possui essa tarefa na sua lista! Nao eh possivel remover");
				}
			}
		}
	}

	private Boolean listIsEmpty(HttpServletResponse resp) throws IOException {
		if (tasksList.isEmpty()) {
			resp.getWriter().append("Sua lista de tarefas estah vazia!");
			return true;
		}
		return false;
	}

	private TaskPriority searchPriority(String myPriority) {
		try { 
			return TaskPriority.getPriority(myPriority);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Prioridade informada invalida!! Informe entre: alta, media ou baixa");
		}
		
	}
}
