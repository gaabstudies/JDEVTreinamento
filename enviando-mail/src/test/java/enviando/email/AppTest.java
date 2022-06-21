package enviando.email;

public class AppTest {

	@org.junit.Test
	public void testeEmail() throws Exception {
		
		StringBuilder stringBuilderTextoemail = new StringBuilder();
		
		stringBuilderTextoemail.append("Olá,<br><br>");
		stringBuilderTextoemail.append("Você está recebendo o acesso ao curso de Java.<br><br/>");
		stringBuilderTextoemail.append("Para ter acesso clique no botão abaixo.<br/><br/>");

		stringBuilderTextoemail.append("<b>Login:</b> gaabstudies@gmail.com<br/>");
		stringBuilderTextoemail.append("<b>Senha:</b> *******<br/><br/>");
		stringBuilderTextoemail.append("<a target=\"_blank\" href=\"http://projetojavaweb.com/certificado-aluno/login\" style=\"color:#252a7; padding: 14px 25px; text-align:center; text-decoration: nome; display:inline-block; border-radius:30px; font-size:20px; font-family:courier; border: 3px solid green; background-color:#99DA39;\">Acessar Portal do Aluno</a><br><br>");
		stringBuilderTextoemail.append("<span style=\"font-size:8px\">Ass.: GabrielaFabiola</span>");



		ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail(" gaabstudies@gmail.com",
															"GabrielaFabiola-Curso JDEV",
															"Testando e-mail com java",
															stringBuilderTextoemail.toString());/** esses são os endereços que irão receber*/

		enviaEmail.enviarEmailAnexo(true);
	}

}
