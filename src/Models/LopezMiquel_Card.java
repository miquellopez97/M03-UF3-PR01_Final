package Models;

public class LopezMiquel_Card {
    final static String RED = "\033[31m", RESET = "\u001B[0m";
    private String name;
    private String surname;
    private int position;
    private float ovr;
    private int atk;
    private int def;
    private String team;
    private String idCard;

    public LopezMiquel_Card(String name, String surname, int position, int atk, int def, String team, String idCard) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.ovr = (atk+def)/2f;
        this.atk = atk;
        this.def = def;
        this.team = team;
        this.idCard = idCard;
    }

    public LopezMiquel_Card(String name, String surname, int position, int atk, int def, String team) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.ovr = (atk+def)/2f;
        this.atk = atk;
        this.def = def;
        this.team = team;
    }

    @Override
    public String toString() {
        return  name + " " + surname +  RED + " POS: " + RESET + position +RED + " OVR: " + RESET + ovr + RED + " ATK: " + RESET + atk + RED + " DEF: " +
                RESET + def + RED + " TEAM: " + RESET + team;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        LopezMiquel_Card cardToCompare = (LopezMiquel_Card) obj;

        return this.idCard.equalsIgnoreCase(cardToCompare.idCard);
    }

    public String getTeam() {
        return team;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public float getOvr() {
        return ovr;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }
}
