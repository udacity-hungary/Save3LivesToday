package hu.intellicode.savethreelivestoday.ui.map;


import java.util.ArrayList;
import java.util.List;

class DummyRepository {

    static List<DonationPoint> getAllDonationPoints(){

        DonationPoint p1 = new DonationPoint();
        p1.setName("Országos vérellátó szolgálat");
        p1.setAddress("Budapest, Karolina út 19-21, 1113");
        p1.setCoordinates(47.4796092,19.0288294);

        DonationPoint p2 = new DonationPoint();
        p2.setName("Péterfy Sándor utcai Területi Vérellátó");
        p2.setAddress("Budapest, Péterfy Sándor u. 14, 1076");
        p2.setCoordinates(47.5018107,19.0762466);

        DonationPoint p3 = new DonationPoint();
        p3.setName("Magyar Vöröskereszt Székház");
        p3.setAddress("Budapest, Arany János u. 31, 1051");
        p3.setCoordinates(47.4910036,19.0380177);

        DonationPoint p4 = new DonationPoint();
        p4.setName("Délpesti Területi Vérellátó");
        p4.setAddress("1204 Budapest, Köves u. 2");
        p4.setCoordinates(47.4247281,19.1289568);

        List<DonationPoint> donationPoints = new ArrayList<>();
        donationPoints.add(p1);
        donationPoints.add(p2);
        donationPoints.add(p3);
        donationPoints.add(p4);

        return donationPoints;
    }
}
