package br.ufg.inf.backend.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

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

		try {
			myNewTask = req.getParameter("task");
			myNewPriority = req.getParameter("priority");

			TaskPriority validatePriority = searchPriority(myNewPriority);

			listIsEmpty(resp);

			for (Task task : tasksList) {
				if (task.getTaskName() == myNewTask) {
					task.setTaskPriority(validatePriority);
					resp.getWriter().append("Tarefa adicionada com sucesso!!");
					return;
				}
			}

			resp.getWriter().append("Tarefa nao encontrada");

		} catch (Exception e) {
			resp.getWriter().append(e.getMessage());
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

	}

	private Boolean listIsEmpty(HttpServletResponse resp) throws IOException{
		if (tasksList.isEmpty()) {
			resp.getWriter().append("Sua lista de tarefas estah vazia!");
			return true;
		}
		return false;
	}

	private TaskPriority searchPriority(String myPriority ) {
		TaskPriority priority = null;
		try {
			 priority = TaskPriority.getPriority(myPriority);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Prioridade informada invalida!! Informe entre: alta, media ou baixa");
		}
		return priority;
	}
}
