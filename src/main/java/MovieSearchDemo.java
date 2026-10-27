
public class MovieSearchDemo {

    public static void main(String[] args) {

        MovieSearchService search =
                new MovieSearchService();

        String result =
                search.prepareSearch("Black Panther");

        System.out.println(result);
    }
}
