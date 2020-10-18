import dao.MovieController;
import dao.PersonController;
import database.Database;
import model.Actor;
import model.Director;
import model.Movie;
import model.Person;

import java.sql.SQLException;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        try {
            String names[]={"Roland Gilpin","Lashunda Lindahl","Meri Duenas","Christin Winkles","Sarai Roehrig","Barbie Saari","Solange Kephart","Elly Ardis","Elmer Eden","Moshe Bondurant","Annice Ragin","Ivelisse Wedderburn","Oralee Whitty","Shellie Razor","Tai Connally","Vernell Claussen","Michal Brammer","Mozelle Mushrush","Melodi Gunther","Karen Nettles","Reva Amsler","Melodee Tsao","Fernande Hile","Isaura Bagnall","Ava Brugnoli","Madie Mcquiston","Jesusita Thomson","Rufina Grund","Paige Chretien","Reed Work","Selma Wellner","Heather Leighton","Chad Pera","Crystle Kula","Nestor Brezinski","Lakisha Molitor","Delma Kingsland","Melva Sadowski","Krishna Laxson","Dorthea Koo","Lila Bartos","Audra Esparza","Waltraud Audet","Elicia Conant","Madeline Dogan","Yajaira Opp","Gaynell Tamayo","Gil Sharma","Carmon Switzer","Robbi Feucht","Jody Thom","Syreeta Pallas","Rod Whiten","Donnette Hoosier","Mariann Lewison","Shalon Wilmore","Shanae Lamborn","Eduardo Brar","Gaynelle Propp","Kathryne Netzer","Minda Bonilla","Vivan White","Helga Swink","Elia Burge","Ira Forrester","Sacha Gerardo","Sylvia Maland","Sandie Bastien","Damaris Hendrie","Jefferson Wiseley","Amberly Pridgeon","Bernetta Mcculley","Janeth Hohlt","Shawnda Liggett","Rudolph Sasser","Julietta Rydberg","Argelia Vangundy","Catherin Cecena","Everette Secord","Christena Markwell","Neomi Tustin","Candy Mansfield","Denise Bodiford","Jovita Saul","Robbi Blomberg","Hye Lambson","Arnette Torrey","Marin Stifter","Creola Hammersmith","Dixie Packer","Crystal Brower","Eloy Amyx","Chuck Neloms","Caryn Wasserman","Tad Closson","Sunny Dare","Warren Clodfelter","Joannie Cerna","Mickey Hunsicker","Aleen Kirven","Belia Lueders","Eleanor Burgher","Chong Clift","Farrah Pendelton","Rhett Jeanbart","Margareta Patino","Maple Aranda","Soila Thibeaux","Sixta Schmidtke","Eloisa Than","Brandon Glowacki","Lawanda Sedor","Felice Greaver","Wen Meadors","Marcellus Gerrard","Rocco Godinez","Stephan Baber","Anissa Chard","Tama Dakin","Joann Mccoy","Ernestine Smart","Jacqulyn Sprankle","Evelia Evan","Jaquelyn Candler","Benton Rosso","Lawana Forest","Noelia Kennedy","Yung Westerfield","Teodora Telfer","Pattie Bonelli","Denis Ting","Darrell Stell","Lenita Swofford","Hana Millay","Lu Eveland","Adelaida Giunta","Lavone Iliff","Perla Hinesley","Terra Mcdaniels","Nathaniel Arevalo","Lakendra Forsyth","Cliff Ficklin","Karisa Portnoy","Dagmar Tuthill","Lennie Wright","Hillary Mcglinchey","Tomasa Peele","Abby Fu","Elvin Bays","Brandi Mcclean","Andria Mandel","Soraya Larson","Chana Bunt","Tameka Hendershott","Karan Resto","Reba Le","Shea Hick","Georgianna Hargreaves","Eleonor Spry","Lila Aguillon","Colby Mcevoy","Christal Qualey","Seymour Horak","Nenita Ellwood","Maya Holl","Hershel Laurence","Billy Mellen","Mabel Garrett","Alicia Redmon","Minna Lilly","Luvenia Joyal","Bobby Kolstad","Katlyn Demark","Scarlet Lilley","Nelly Spilman","Eugena Wise","Charita Straw","Danielle Becnel","Joselyn Najar","Hollis Spigner","Hillary Newton","Martin Cisneros","Tomoko Wyland","Leanna Chavers","Donya Garzon","Sandi Derby","Roxy Regnier","Mei Hultgren","Alaine Meister","Shanon Buis","Nikole Desjardins","Gilberte Olmsted","Aleida Scheer","Bridget Mcclusky","Judi Cornelison","Wilhelmina Mcmartin","Dwayne Vester","Joaquina Myron","Jamika Babst","Hilary Delavega"};
            String movieNames[]={"Red Cliff","Cold Pursuit","Jeremiah Terminator LeRoy","Hard Target","Avatar","What Men Want","Killing Season","A Bad Moms Christmas","Wrong Turn 2: Dead End","Force Majeure","Look Who`s Talking Too","Anon","Harry Potter and the Chamber of Secrets","Jurassic World","Rough Night","Inception","Hellboy","Star Wars: The Last Jedi","Mile 22","Avengers: Age of Ultron","Instant Family","Jurassic World: Fallen Kingdom","Basic Instinct 2","Inside Out","Frankenstein","Twilight","Triple Frontier","The Cat Returns","The Man Who Knew Too Much","The First Purge","Aquaman","The Belko Experiment","Hunter Killer","How to Train Your Dragon: The Hidden World","Truth or Dare","Avengers: Endgame","High Life","The Wind That Shakes the Barley","Che: Part One","Deadpool","Iron Man 2","BlacKkKlansman","Eighth Grade","The Mule","Detective Conan: The Fist of Blue Sapphire","Guardians of the Galaxy Vol. 2","Glass","Pitch Perfect 3","The Dark Knight","It`s a Wonderful Life"};
            PersonController persons = new PersonController();
            MovieController movies = new MovieController();

            for (int i=0;i<150;i++){
                persons.create(new Actor(0,names[i]));
            }
            for (int i=150;i<200;i++){
                persons.create(new Director(0,names[i]));
            }

            Database.commit();

            for (int i=0;i<50;i++){
                movies.create(new Movie(0,movieNames[i], (Director)persons.findById(i+151)));
            }

            Database.commit();

            for (int i=0;i<50;i++){
                Random random=new Random();
                int actorsNumber=random.nextInt(20)+1;
                for (int j=1;j<=actorsNumber;j++){
                    int actorId=random.nextInt(150)+1;
                    movies.add(movies.findByName(movieNames[i]),(Actor)persons.findById(actorId));
                }
            }

            Database.commit();

            Database.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.rollback();
        }
    }
}
