import java.util.ArrayList;
import java.util.List;
public class RepositorioDeLinks {
    private List<Link> links;

    public RepositorioDeLinks() {
        this.links = new ArrayList<>();
    }

    public void adicionarLink(Link link) {
        this.links.add(link);
    }

    public void removerLink(Link link) {
        this.links.remove(link);
    }

    public List<Link> getLinks() {
        return this.links;
    }
}

