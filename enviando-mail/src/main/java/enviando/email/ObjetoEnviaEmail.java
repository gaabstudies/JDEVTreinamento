package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.xml.simpleparser.NewLineHandler;

public class ObjetoEnviaEmail {

	private String userName = "gaabstudies@gmail.com";
	private String senha = "Ggjv@4515";

	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";

	public ObjetoEnviaEmail(String listaDestinatarios, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatarios;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}

	public void enviarEmail(boolean envioHtml) throws Exception {

		/* Configurando as propeiedades de conexão */
		Properties properties = new Properties();

		properties.put("mail.smtp.ssl.trust", "*");/* Autenticação com segurança SSL */
		properties.put("mail.smtp.auth", "true");/* Autorização */
		properties.put("mail.smtp.starttls", "true"); /* Autenticação */
		properties.put("mail.smtp.host", "smtp.gmail.com"); /* Servidor gmail Google */
		properties.put("mail.smtp.port", "465");/* Porta do servidor */
		properties.put("mail.smtp.socketFactory.port", "465");/* Expecifica a porta a ser conectada pelo socket */
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");/* Classe socket de conexão ao SMTP */

		/* Configurando conexão com servidor Gmail */
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});

		/* Realizando o envio de e-mail */
		/* Lista de endereços que vamos enviar */
		Address[] toUser = InternetAddress.parse(listaDestinatarios);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); /* Quem está enviano */
		message.setRecipients(Message.RecipientType.TO, toUser);/* Email de destino */
		message.setSubject(assuntoEmail);/* Assunto do e-mail */

		if (envioHtml) {
			message.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			message.setText(textoEmail);
		}

		Transport.send(message);
	}

	/* Enviando e-mail com PDF em anexo */
	public void enviarEmailAnexo(boolean envioHtml) throws Exception {

		/* Configurando as propeiedades de conexão */
		Properties properties = new Properties();

		properties.put("mail.smtp.ssl.trust", "*");/* Autenticação com segurança SSL */
		properties.put("mail.smtp.auth", "true");/* Autorização */
		properties.put("mail.smtp.starttls", "true"); /* Autenticação */
		properties.put("mail.smtp.host", "smtp.gmail.com"); /* Sercidor gmail Google */
		properties.put("mail.smtp.port", "465");/* Porta do servidor */
		properties.put("mail.smtp.socketFactory.port", "465");/* Expecifica a porta a ser conectada pelo socket */
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");/* Classe socket de conexão ao SMTP */

		/* Configurando conexão com servidor Gmail */
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});

		/* Realizando o envio de e-mail */
		/* Lista de endereços que vamos enviar */
		Address[] toUser = InternetAddress.parse(listaDestinatarios);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); /* Quem está enviano */
		message.setRecipients(Message.RecipientType.TO, toUser);/* Email de destino */
		message.setSubject(assuntoEmail);/* Assunto do e-mail */

		/* Parte 1 do Email- Que é o texto e a descrição do e-mail */
		MimeBodyPart corpoEmail = new MimeBodyPart();

		if (envioHtml) {
			corpoEmail.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			corpoEmail.setText(textoEmail);
		}

		/* Parte 2 do email- para envio de mais de um anexo em PDF */

		List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
		arquivos.add(simuladorDePDF());/* Certificado */
		arquivos.add(simuladorDePDF());/* nota fiscal */
		arquivos.add(simuladorDePDF());/* documento texto */
		arquivos.add(simuladorDePDF()); /* Imagem */

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corpoEmail);

		int index = 0;
		for (FileInputStream fileInputStream : arquivos) {

			/* Parte 2 do e-mail que são os anexo em pdf */
			MimeBodyPart anexoEmail = new MimeBodyPart();

			/*
			 * Onde é passado o simuladorDePDf você passa o seu arquivo gravado no banco e
			 * dados
			 */
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
			anexoEmail.setFileName("anexoemail" + index + ".pdf");

			multipart.addBodyPart(anexoEmail);

			index++;
		}

		/*
		 * Parte 2 do email- para envio de um anexo em PDF MimeBodyPart anexoEmail = new
		 * MimeBodyPart(); anexoEmail.setDataHandler(new DataHandler(new
		 * ByteArrayDataSource(simuladorDePDF(), "application/pdf")));
		 * anexoEmail.setFileName("anexoemail.pdf");
		 * 
		 * Multipart multipart = new MimeMultipart(); multipart.addBodyPart(corpoEmail);
		 * multipart.addBodyPart(anexoEmail);
		 */

		message.setContent(multipart);

		Transport.send(message);
	}

	/*
	 * Criando o Simulador de PDF com Itext Esse método simulao PDF ou quaquer
	 * arquivo que possa ser enviado por anexo no email. você pode pegar o arquivo
	 * no seu banco de dadosbase64,byte[],stream de arquivos. Pode estar em um banco
	 * de dados , ou em uma pasta Retorna um PDF em branco com o texto no parágrafo
	 */
	private FileInputStream simuladorDePDF() throws Exception {
		Document document = new Document();
		File file = new File("Fileanexo.pdf");/* Criando em tempo de execução */
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));/*
																	 * Estou pegando esse documento que vai ser o PDF,
																	 * escrevendo nele, instanciando e passar o doc
																	 */
		document.open();/* Abrimos o doc */
		document.add(new Paragraph(
				"Conteúdo do PDF anexo com Java, esse texto é do PDF"));/* adicionando o texto do documento */
		document.close();
		return new FileInputStream(file);

	}

}
