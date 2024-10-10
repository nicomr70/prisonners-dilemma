package fr.uga.miage.m1.my_project.Strategies;

import fr.uga.miage.m1.my_project.Enums.TypeAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;



@Data
@AllArgsConstructor
public abstract class Strategie  {

    private String name;

    public Strategie() {
        this.name = this.getClass().getSimpleName();
    }

    public abstract TypeAction getAction(List<TypeAction> actions, int dernierResultat);

    public TypeAction getLastAction(List<TypeAction> actions) {
        return actions.get(actions.size() - 1);
    }
}
