import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainApp {
    public static void scriere(List<Angajat> lista) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            File file = new File("src/main/resources/angajati.json");
            mapper.writeValue(file, lista);
            System.out.println("Datele au fost salvate cu succes in fisierul angajati.json!  ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Angajat> citire() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            File file = new File("src/main/resources/angajati.json");
            List<Angajat> angajati = mapper
                    .readValue(file, new TypeReference<List<Angajat>>() {
                    });
            System.out.println("Datele au fost citite cu succes din fisier! ");
            return angajati;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        var angajati = citire();

        // 1. Afișarea listei de angajați
        System.out.println("1) Lista angajati: ");
        angajati.forEach(System.out::println);

        // 2. Angajați cu salariul peste 2500 RON
        System.out.println("2) Angajati cu salariul>2500: ");
        angajati.stream()
                .filter(a -> a.getSalariul() > 2500)
                .forEach(System.out::println);

        // 3. Angajați în aprilie anul trecut (șefi/directori)
        var an_trecut = Year.now().getValue() - 1;
        System.out.println("3) Angajati sefi/directori angajati in luna aprilie din anul: " + an_trecut);
        var angajatiAprilieAnTrecut = angajati.stream()
                .filter(a -> a.getData_angajarii().getYear() == an_trecut
                        && a.getData_angajarii().getMonthValue() == 4)
                .filter(a -> {
                    var p = a.getPost().toLowerCase();
                    return p.contains("sef") || p.contains("director");
                })
                .collect(Collectors.toList());
        angajatiAprilieAnTrecut.forEach(System.out::println);

        // 4. Angajați fără funcție de șef/director, ordonați descrescător după salariu
        System.out.println("4) Angajati fara functie sef/director - ordonati descrescator dupa salariu: ");
        angajati.stream()
                .filter(a -> {
                    var p = a.getPost().toLowerCase();
                    return !(p.contains("sef") || p.contains("director"));
                })
                .sorted(Comparator.comparing(Angajat::getSalariul).reversed())
                .forEach(System.out::println);

        // 5. Lista cu numele angajaților scrisă cu majuscule
        System.out.println("5) Lista angajati cu majuscule: ");
        var numeAngajatiCuMajuscule = angajati.stream()
                .map(a -> a.getNume().toUpperCase())
                .collect(Collectors.toList());
        numeAngajatiCuMajuscule.forEach(System.out::println);

        // 6. Afișarea salariilor mai mici de 3000 de RON
        System.out.println("6) Afisare salarii<3000 de lei: ");
        angajati.stream()
                .map(Angajat::getSalariul)
                .filter(s -> s < 3000)
                .forEach(System.out::println);

        // 7. Primul angajat al firmei (cel mai vechi)
        System.out.println("7) Primul angajat (cel mai vechi): ");
        Optional<Angajat> primul = angajati.stream()
                .min((a1, a2) -> {
                    if (a1.getData_angajarii() == null && a2.getData_angajarii() == null) return 0;
                    if (a1.getData_angajarii() == null) return 1;
                    if (a2.getData_angajarii() == null) return -1;
                    return a1.getData_angajarii().compareTo(a2.getData_angajarii());
                });
        primul.ifPresentOrElse(System.out::println,
                () -> System.out.println("Nu exista angajati in lista! ")
        );
        primul.ifPresentOrElse(a -> System.out.println("Numele cu majuscule: " + a.getNume().toUpperCase()),
                () -> System.out.println("Nu exista angajati in lista! ")
        );

        // 8. Statistici referitoare la salariul angajaților
        System.out.println("8) Statistici salarii: ");

        var statisticaSalariu = angajati.stream()
                .collect(Collectors.summarizingDouble(a -> a.getSalariul()));
        System.out.println("Numar: " + statisticaSalariu.getCount());
        System.out.println("Salariu mediu: " + statisticaSalariu.getAverage());
        System.out.println("Salariu minim: " + statisticaSalariu.getMin());
        System.out.println("Salariu maxim: " + statisticaSalariu.getMax());

        // 9. Verificare dacă există cel puțin un "Ion"
        System.out.println("9) Verificare 'ION' in lista ");
        Optional<Angajat> ionOptional = angajati.stream()
                .filter(a -> {
                    var parti = a.getNume().split("\\s+");
                    for (var p : parti) {
                        if (p.equalsIgnoreCase("Ion")) return true;
                    }
                    return false;
                })
                .findAny();
        ionOptional.ifPresentOrElse(
                a -> System.out.println("Firma are cel puțin un Ion angajat: " + a.getNume()),
                () -> System.out.println("Firma nu are nici un Ion angajat")
        );

        // 10. Numărul de persoane angajate în vara anului precedent
        System.out.println("\n10) Număr persoane angajate în vara anului precedent:");
        var numarVara = angajati.stream()
                .filter(a->a.getData_angajarii().getYear()==an_trecut
                        && (a.getData_angajarii().getMonthValue()==6
                        || a.getData_angajarii().getMonthValue()==7
                        || a.getData_angajarii().getMonthValue()==8))
                .count();
        System.out.println("Nr angajati in vara anului "+an_trecut+": "+numarVara);

    }

}