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

	private String myTask;
	private String myDescription;
	private String myPriority;

	private List<Task> tasksList;

	public TaskServlet() {
		this.tasksList = new ArrayList<>();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		tasksList.add(new Task("Tarefa TEste", "teste", TaskPriority.ALTA));
		
		if (tasksList.isEmpty()) {
			resp.getWriter().append("Sua lista de tarefas estah vazia!");
		} else {
			for (Task task : tasksList) {
				resp.getWriter().println(task.getMyTask() + " - Prioridade: " + task.getPriority());
				resp.getWriter().println("Descricao: " + task.getDescription());
				resp.getWriter().println(tasksList.indexOf(task) + 1);
			}
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			myTask = req.getParameter("task");
			myDescription = req.getParameter("description");
			myPriority = req.getParameter("priority");

			TaskPriority validatePriority = getPriority(myPriority);

			validateListEmpty();

			for (Task task : tasksList) {
				if (task.getMyTask() == myTask) {
					task.setDescription(myDescription);
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
	
	

	private void validateListEmpty() {
		if (tasksList.isEmpty()) {
			throw new RuntimeException(
					"Sua lista de tarefas estah vazia, nao eh possivel atualiza-la com o valor informado!");
		}
	}

	private TaskPriority getPriority(String myPriority) {
		TaskPriority searchPriority = TaskPriority.getPriority(myPriority);
		if (searchPriority == null) {
			throw new RuntimeException("A prioridade nao corresponde! Informe entre: alta, media ou baixa");
		}
		return searchPriority;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		myTask = req.getParameter("task");
		myDescription = req.getParameter("description");
		myPriority = req.getParameter("priority");

		for (Task task : tasksList) {
			if (task.getMyTask() == myTask) {
				resp.getWriter().append("Estah tarefa ja existe!");
				return;
			}
		}

		TaskPriority searchPriority = TaskPriority.getPriority(myPriority);
		if (searchPriority != null) {
			//task.setTaskPriority(searchPriority);
		} else {
			resp.getWriter().append("A prioridade nao corresponde! Informe entre: alta, media ou baixa");
		}

		//tasksList.add(new Task(myTask, myDescription));
		resp.getWriter().append(String.format("Tarefa %s adicionada!!", myTask));

		resp.getWriter().append(String.format(""));
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
