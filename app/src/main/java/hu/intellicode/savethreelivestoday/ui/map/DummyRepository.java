package hu.intellicode.savethreelivestoday.ui.map;


import java.util.ArrayList;
import java.util.List;

// I've set it public so I could use it in MainActivity
// @Arcfej
public class DummyRepository {

    public static List<DonationPoint> getAllDonationPoints() {

        DonationPoint p1 = new DonationPoint();
        p1.setName("Országos vérellátó szolgálat");
        p1.setAddress("Budapest, Karolina út 19-21, 1113");
        p1.setCoordinates(47.4796092, 19.0288294);

        DonationPoint p2 = new DonationPoint();
        p2.setName("Péterfy Sándor utcai Területi Vérellátó");
        p2.setAddress("Budapest, Péterfy Sándor u. 14, 1076");
        p2.setCoordinates(47.5018107, 19.0762466);

        DonationPoint p3 = new DonationPoint();
        p3.setName("Magyar Vöröskereszt Székház");
        p3.setAddress("Budapest, Arany János u. 31, 1051");
        p3.setCoordinates(47.4910036, 19.0380177);

        DonationPoint p4 = new DonationPoint();
        p4.setName("Délpesti Területi Vérellátó");
        p4.setAddress("1204 Budapest, Köves u. 2");
        p4.setCoordinates(47.4247281, 19.1289568);

        DonationPoint p5 = new DonationPoint("Neumann János Szám.Tech.XIV. Bp.",
                "1144 Budapest	Kerepesi út 124.", 47.5046743, 19.1466559);

        DonationPoint p6 = new DonationPoint("Győri Regionális Vérellátó Kp. Intézeti",
                "9023 Győr, Magyar u. 8.", 47.674061, 17.648672);

        DonationPoint p7 = new DonationPoint("Debreceni Regionális Vérell.Kp. Intézeti",
                "4026 Debrecen, Bem tér 19/B. ", 47.5432273, 21.6256986);

        List<DonationPoint> donationPoints = new ArrayList<>();
        donationPoints.add(p1);
        donationPoints.add(p2);
        donationPoints.add(p3);
        donationPoints.add(p4);
        donationPoints.add(p5);
        donationPoints.add(p6);
        donationPoints.add(p7);

        return donationPoints;
    }
}