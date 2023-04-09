import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Main {

    public static ArrayList<String> tumBilgi = new ArrayList<>();
    public static int elitSayı = 0;
    public static int genelSayı = 0;

    public static class Musteri {
        public String ad;
        String soyAd;
        String email;
    }
    public static class bilgiAl extends Musteri{
        public static void Uyebilgisi(Musteri uyeTuru, Scanner inputDosyası){
            System.out.print("Lütfen üye ismini giriniz: ");
            uyeTuru.ad = inputDosyası.next();
            System.out.print("Lütfen üye soyadını giriniz: ");
            uyeTuru.soyAd = inputDosyası.next();
            System.out.print("Lütfen üye mailini giriniz: ");
            uyeTuru.email = inputDosyası.next();
        }
    }

    public static class Mailgonder{
        public static void mail(String mailAdresi, String gonderilecekMail)
        {
            final String kullaniciMail = ""; // gönderici mail adresi
            final String kullaniciSifre = ""; // gönderici şifre
            String aliciMail = mailAdresi;
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(kullaniciMail, kullaniciSifre);
                        }
                    });

            try {

                Message ileti = new MimeMessage(session);
                ileti.setFrom(new InternetAddress(aliciMail));
                ileti.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(aliciMail));
                ileti.setSubject("Ödev");
                ileti.setText(gonderilecekMail);
                Transport.send(ileti);
                System.out.println("Email başarıyla gönderildi");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public static class Mail extends Mailgonder{
        public static void eliteGonder(String alıcıHesap, String gönderilecekMesaj){
            mail(alıcıHesap, gönderilecekMesaj);
        }
        public static void geneleGonder(String alıcıHesap, String gönderilecekMesaj){
            mail(alıcıHesap, gönderilecekMesaj);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        File inputFile = new File("müşteri.txt");
        if (!inputFile.exists()) {
            inputFile.createNewFile();
        }
        String secilenHarf = "Y";
        while (!secilenHarf.equals("H")) {
            FileWriter fileWriter = new FileWriter(inputFile, false); // "true" parametresi, dosyanın sonuna ekleme yapılmasını sağlar
            System.out.println("1) Elit üye ekleme");
            System.out.println("2) Genel üye ekleme");
            System.out.println("3) Mail gönderme");
            System.out.println("Yapmak istediğiniz işlemi rakamla belirtiniz");
            Scanner islem = new Scanner(System.in);
            int _islem = islem.nextInt();
            switch (_islem) {
                case (1):
                    // elit üye ekleme
                    Musteri yeniElit = new Musteri();
                    System.out.print("Lütfen üye ismini giriniz: ");
                    yeniElit.ad = input.next();
                    fileWriter.flush();
                    System.out.print("Lütfen üye soyadını giriniz: ");
                    yeniElit.soyAd = input.next();
                    fileWriter.flush();
                    System.out.print("Lütfen üye mailini giriniz: ");
                    yeniElit.email = input.next();
                    fileWriter.flush();
                    if (elitSayı == 0 && genelSayı == 0) {
                        fileWriter.write("#Elit üyeler\n");
                        fileWriter.append(yeniElit.ad + "\t" + yeniElit.soyAd + "\t" + yeniElit.email + "\n");
                        tumBilgi.add("#Elit üyeler\n");
                        tumBilgi.add(yeniElit.ad + "\t" + yeniElit.soyAd + "\t" + yeniElit.email + "\n");
                    }
                    else if (elitSayı == 0 && genelSayı != 0){
                        ArrayList<String> aList = new ArrayList<>();
                        aList.add("#Elit üyeler\n");
                        aList.add(yeniElit.ad + "\t" + yeniElit.soyAd + "\t" + yeniElit.email + "\n");
                        for (String items: tumBilgi)
                            aList.add(items);
                        tumBilgi.clear();
                        for (String allitem: aList)
                            tumBilgi.add(allitem);
                        fileWriter.write("");
                        for (String eachline: tumBilgi)
                            fileWriter.append(eachline);
                    }
                    else if (elitSayı != 0 && genelSayı == 0){
                        tumBilgi.add(yeniElit.ad + "\t" + yeniElit.soyAd + "\t" + yeniElit.email + "\n");
                        fileWriter.write("");
                        for (String eachline: tumBilgi) {
                            fileWriter.append(eachline);
                        }
                    }
                    else {
                        int i = -1;
                        ArrayList<String> aList = new ArrayList<>();
                        for (String satırlar : tumBilgi) {
                            if (i == elitSayı) {
                                aList.add(yeniElit.ad + "\t" + yeniElit.soyAd + "\t" + yeniElit.email + "\n");
                            }
                            aList.add(satırlar);
                            i++;
                        }
                        tumBilgi.clear();
                        for (String yeniSatırlar: aList)
                            tumBilgi.add(yeniSatırlar);
                        fileWriter.write("");
                        for (String eachline: tumBilgi)
                            fileWriter.append(eachline);
                    }
                    fileWriter.flush();
                    elitSayı += 1;
                    System.out.println("Devam etmek ister misiniz? E/H");
                    secilenHarf = input.next();
                    fileWriter.flush();
                    break;
                case (2):
                    // genel üye ekleme
                    Musteri yeniGenel = new Musteri();
                    System.out.print("Lütfen üye ismini giriniz: ");
                    yeniGenel.ad = input.next();
                    fileWriter.flush();
                    System.out.print("Lütfen üye soyadını giriniz: ");
                    yeniGenel.soyAd = input.next();
                    fileWriter.flush();
                    System.out.print("Lütfen üye mailini giriniz: ");
                    yeniGenel.email = input.next();
                    fileWriter.flush();
                    if (elitSayı == 0 && genelSayı == 0) {
                        fileWriter.write("#Genel üyeler\n");
                        fileWriter.append(yeniGenel.ad + "\t" + yeniGenel.soyAd + "\t" + yeniGenel.email + "\n");
                        tumBilgi.add("#Genel üyeler\n");
                        tumBilgi.add(yeniGenel.ad + "\t" + yeniGenel.soyAd + "\t" + yeniGenel.email + "\n");
                    }
                    else if (elitSayı == 0 && genelSayı != 0){
                        tumBilgi.add(yeniGenel.ad + "\t" + yeniGenel.soyAd + "\t" + yeniGenel.email + "\n");
                        fileWriter.write("");
                        for (String eachline: tumBilgi)
                            fileWriter.append(eachline);
                    }
                    else if (elitSayı != 0 && genelSayı == 0){
                        tumBilgi.add("#Genel Üyeler\n");
                        tumBilgi.add(yeniGenel.ad + "\t" + yeniGenel.soyAd + "\t" + yeniGenel.email + "\n");
                        fileWriter.write("");
                        for (String eachline: tumBilgi){
                            fileWriter.append(eachline);
                        }
                    }
                    else {
                        tumBilgi.add(yeniGenel.ad + "\t" + yeniGenel.soyAd + "\t" + yeniGenel.email + "\n");
                        fileWriter.write("");
                        for (String eachline: tumBilgi){
                            fileWriter.append(eachline);
                        }
                    }
                    fileWriter.flush();
                    genelSayı += 1;
                    System.out.println("Devam etmek ister misiniz? E/H");
                    secilenHarf = input.next();
                    fileWriter.flush();
                    fileWriter.flush();
                    break;
                case (3):
                    fileWriter.flush();
                    System.out.println("1) Elit üyelere mail gönderme");
                    System.out.println("2) Genel üyelere mail gönderme");
                    System.out.println("3) Tüm üyelere mail gönderme");
                    System.out.println("Yapmak istediğiniz işlemi rakamla belirtiniz");
                    int yeniIslem = islem.nextInt();
                    fileWriter.flush();
                    BufferedReader mesajreader = new BufferedReader(new InputStreamReader(System.in));
                    switch (yeniIslem) {
                        case (1):
                            // elit üyelere mail
                            if (elitSayı == 0){
                                System.out.println("Elit üye yok.");
                                continue;
                            }
                            System.out.println("Gönderilecek mesajı girin");
                            String mesaj = mesajreader.readLine();
                            fileWriter.flush();
                            for (int i = 1; i < elitSayı + 1; i++){
                                String guncelSatır = tumBilgi.get(i);
                                String[] guncelArray = guncelSatır.split("\t");
                                String guncelKullanıcı = guncelArray[2];
                                Mail.eliteGonder(guncelKullanıcı, mesaj);
                            }
                            break;
                        case (2):
                            //genel üyelere mail
                            if (genelSayı == 0){
                                System.out.println("Genel üye yok.");
                                continue;
                            }
                            System.out.println("Gönderilecek mesajı girin");
                            String yenimesaj = mesajreader.readLine();
                            fileWriter.flush();
                            if (elitSayı != 0) {
                                for (int i = elitSayı + 2; i < elitSayı + genelSayı + 2; i++) {
                                    String guncelSatır = tumBilgi.get(i);
                                    String[] guncelArray = guncelSatır.split("\t");
                                    String guncelKullanıcı = guncelArray[2];
                                    Mail.eliteGonder(guncelKullanıcı, yenimesaj);
                                }
                            }
                            else {
                                for (int i = 1; i < genelSayı + 1; i++) {
                                    String guncelSatır = tumBilgi.get(i);
                                    String[] guncelArray = guncelSatır.split("\t");
                                    String guncelKullanıcı = guncelArray[2];
                                    Mail.eliteGonder(guncelKullanıcı, yenimesaj);
                                }
                            }
                            break;
                        case (3):
                            // tüm üyelere mail
                            // tüm üyelere mail
                            System.out.println("Gönderilecek mesajı girin");
                            String Mesaj = mesajreader.readLine();
                            fileWriter.flush();
                            if (elitSayı == 0 && genelSayı == 0){
                                System.out.println("Kullanıcı yok");
                            }
                            if (elitSayı == 0 && genelSayı != 0){
                                for (int i = 1; i < genelSayı + 1; i++) {
                                    String guncelSatır = tumBilgi.get(i);
                                    String[] guncelArray = guncelSatır.split("\t");
                                    String guncelKullanıcı = guncelArray[2];
                                    Mail.eliteGonder(guncelKullanıcı, Mesaj);
                                }
                            }
                            if (elitSayı != 0 && genelSayı == 0){
                                for (int i = 1; i < elitSayı + 1; i++){
                                    String guncelSatır = tumBilgi.get(i);
                                    String[] guncelArray = guncelSatır.split("\t");
                                    String guncelKullanıcı = guncelArray[2];
                                    Mail.eliteGonder(guncelKullanıcı, Mesaj);
                                }
                            }
                            if (elitSayı != 0 && genelSayı != 0){
                                for (int i = 1; i < elitSayı + 1; i++){
                                    String guncelSatır = tumBilgi.get(i);
                                    String[] guncelArray = guncelSatır.split("\t");
                                    String guncelKullanıcı = guncelArray[2];
                                    Mail.eliteGonder(guncelKullanıcı, Mesaj);
                                }
                                for (int i = elitSayı + 2; i < elitSayı + genelSayı + 2; i++) {
                                    String guncelSatır = tumBilgi.get(i);
                                    String[] guncelArray = guncelSatır.split("\t");
                                    String guncelKullanıcı = guncelArray[2];
                                    Mail.eliteGonder(guncelKullanıcı, Mesaj);
                                }
                            }
                    }
            }
        }

    }
}