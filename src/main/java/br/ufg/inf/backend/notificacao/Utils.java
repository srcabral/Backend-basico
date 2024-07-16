package br.ufg.inf.backend.notificacao;

public class Utils {

	public static String tipoOperacao(String operacao) {
		String tipo;

		switch (operacao) {
		case "adicionar": {
			tipo = "adicionamento";
			break;
		}
		case "atualizar": {
			tipo = "atualizacao";
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + operacao);
		}
		
		return tipo;
	}
}
