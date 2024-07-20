package br.ufg.inf.backend.notificacao;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.backend.tasks.model.Task;
import br.ufg.inf.backend.tasks.model.TaskPriority;

public class TaskService {

	private static List<Task> tasksList = new ArrayList<>();

	private NotificacaoService notificacaoService;

	public TaskService() {
	}

	public TaskService(NotificacaoService notificacaoService) {
		this.notificacaoService = notificacaoService;
	}

	public String adicionar(String myTask, String myPriority) {
		Task newTask = new Task(myTask, searchPriority(myPriority));

		for (Task task : tasksList) {
			if (task.getTaskName().equals(newTask.getTaskName())) {
				return "Estah tarefa ja existe!";
			}
		}

		tasksList.add(newTask);

		return "Tarefa adicionada com sucesso!\n" + notificacaoService.enviarNotificacao("adicionar");
	}

	public String atualizar(String newTask, String newPriority, int indice) {
		for (Task task : tasksList) {
			if (tasksList.indexOf(task) == indice - 1) {
				task.setTaskName(newTask);
				task.setTaskPriority(searchPriority(newPriority));
				return "Tarefa atualizada com sucesso!\n" + notificacaoService.enviarNotificacao("atualizar");
			}
		}

		return String.format("Tarefa nao encontrada. Sua lista possui apenas %d tarefa(s) cadastrada(s)",
				tasksList.size(), indice);
	}

	public String remover(String myTask) {
		
		if (listIsEmpty()) {
			return "Sua lista de tarefas estah vazia!";
		} else {
			for (Task task : tasksList) {
				if (task.getTaskName().equals(myTask)) {
					tasksList.remove(tasksList.indexOf(task));
					return String.format("Tarefa %s removida com sucesso!", myTask);
				} 
			}
			return "Voce nao possui essa tarefa cadastrada na sua lista!";
		}
	}

	public String listar() {
		if (tasksList.size() == 0) {
			return "Você não possui tarefas!";
		}
		String myTasks = "";
		for (Task task : tasksList) {
			myTasks += tasksList.indexOf(task) + 1 + ": " + task.getTaskName() + " - Prioridade: "
					+ task.getTaskPriority() + "\n";
		}
		return myTasks;
	}

	private TaskPriority searchPriority(String myPriority) {
		try {
			return TaskPriority.getPriority(myPriority);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Prioridade informada invalida!! Informe entre: alta, media ou baixa");
		}

	}
	
	private Boolean listIsEmpty() {
		if (tasksList.isEmpty()) {
			return true;
		}
		return false;
	}
}
