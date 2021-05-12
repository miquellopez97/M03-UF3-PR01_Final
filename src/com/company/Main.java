package com.company;

import Models.LopezMiquel_Card;
import Models.LopezMiquel_Player;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    final static String RED = "\033[31m", RESET = "\u001B[0m";
    static ArrayList<LopezMiquel_Player> playersList = new ArrayList<>();
    static ArrayList<LopezMiquel_Card> mainDeck = new ArrayList<>();
    static ArrayList<LopezMiquel_Card> botFloor = new ArrayList<>();
    static ArrayList<LopezMiquel_Card> playerFloor = new ArrayList<>();
    static int[] scoreboard = new int[2];

    public static void main(String[] args) {
        int option;

        do {
            option=menu();

            switch (option) {
                case 1 -> userOptions();
                case 2 -> openPacket();
                case 3 -> {
                    dealCards(0);
                    dealCards(1);
                    match();
                }
                case 4 -> addPlayerManual();
                case 5 -> howToPlay();
                case 6 -> disponibleCards();
                case 7 -> {
                    printBbdd(mainDeck);
                    System.out.println("Has escogido salir");
                }
            }
        }while (option!=7);

    }

    /**
     * Exporta el main deck final, por si se han anadido jugadores de forma manual
     * @param x Nueva maindeck
     */
    public static void printBbdd(ArrayList<LopezMiquel_Card> x) {
        try {
            File document = new File("Cards.txt");
            PrintStream output= new PrintStream(document);
            for (LopezMiquel_Card lopezMiquel_card : x) {
                output.print(lopezMiquel_card.getName() + ";" +
                        lopezMiquel_card.getSurname() + ";" +
                        lopezMiquel_card.getPosition() + ";" +
                        lopezMiquel_card.getAtk() + ";" +
                        lopezMiquel_card.getDef() + ";" +
                        lopezMiquel_card.getTeam() + ";" +
                        lopezMiquel_card.getIdCard() + ";" +
                        "\n");
            }
            output.close();
        }catch (Exception ignored){}

    }

    /**
     * Introduce las cartas de la bbdd en el maindeck
     */
    public static void readBbdds(){
        try {
            File document = new File("Cards.txt");
            try (Scanner sc = new Scanner(document)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] values = line.split(";");
                    LopezMiquel_Card x = new LopezMiquel_Card(values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]),
                            Integer.parseInt(values[4]), values[5], values[6]);
                    mainDeck.add(x);
                }
            } catch (Exception ignored) {
            }
        }catch (Exception ignored){}
    }

    /**
     * Cartas disponibles en el juego
     */
    public static void disponibleCards(){
        System.out.println( "******************************* CARTAS DISPONIBLES ************************************\n"+
                RED + "Prime Legends:\n" + RESET + "     Jugadores legendarios en su mejor estado de forma.\n"+
                RED + "Future Stars:\n" + RESET + "     Jugadores que actualmente no estan en la NBA pero nadie duda que estaran.\n"+
                RED + "Celebrity:\n" + RESET + "     Jugadores que han participado en el All-Star game como celebridades.\n"+
                RED + "Rosters NBA (2020-2021):\n" + RESET +
                "     -Miami Heat" + "     -Los Angeles Lakers" + "     -Dallas Mavericks\n"+
                "     -Brooklyn Nets"+ "     -Milwaukee Bucks"+ "     -Denver Nuggets\n"+
                "     -Philadelphia 76ers"+"     -Atlanta Hawks"+ "     -Boston Celtics\n"+
                RED + "Rosters Euroliga (2020-2021):\n" + RESET +
                "     -Real Madrid\n" + "     -FC Barcelona\n" +
                RED + "Recuento de cartas:\n" + RESET +
                RED + "Bases: "+ RESET + countCards(1) + RED + " Escoltas: "+ RESET + countCards(2) +
                RED + " Aleros: "+ RESET + countCards(3) + RED + " Ala pivots: "+ RESET + countCards(4) +
                RED + " Pivots: "+ RESET + countCards(5) + RED + " Total: "+ RESET + mainDeck.size()+ "\n"+
                "******************************* CARTAS DISPONIBLES ************************************\n");
    }

    public static int countCards(int postion){
        int x = 0;
        for (LopezMiquel_Card lopezMiquel_card : mainDeck) {
            if (lopezMiquel_card.getPosition() == postion) {
                x++;
            }
        }
        return x;
    }

    /**
     * Explicación de como funciona el juego
     */
    public static void howToPlay(){
        System.out.println(
                "*********************************** HOW TO PLAY ***********************************************\n"+
                        "Este juego trata sobre una simulación de un partido de baloncesto, es un juego de cartas donde\n" +
                        "cada carta es un jugador de la NBA. Tendras a tu disposición 5 cartas las cuales son\n" +
                        "base(1), un escolta(2), un alero(3), un ala-pívot(4) y un pívot(5).\n" +
                        "Cada carta contiene 3 valores ATK, DEF y OVR. Estos valores son asignados por el creador\n" +
                        "del juego, el OVR es la media entre el ataque y la defensa.\n"+
                        "El modo de juego se basa en la siguiente forma, un partido de baloncesto consiste\n"+
                        "en 4 parciales, en este juego cada parcial esta definido por 4 acciones.\n"+
                        "Hay tres tipos de acciones:\n" +
                        "   Acción defensiva:\n" +
                        "   Se compara la puntuación defensiva de la carta escogida versus el ataque de la carta rival\n" +
                        "   Acción ofensiva:\n" +
                        "   Se compara la puntuación ofensiva de la carta escogida versus la defensa de la carta rival\n" +
                        "   2 contra 2:\n" +
                        "   Se compara la puntuación OVR de la carta escogida versus el OVR de la carta rival\n" +
                        "El orden de las acciones de los periodos sera:\n" +
                        "   Primera acción defensiva\n"+
                        "   Segunda acción ofensiva\n"+
                        "   Tercera acción 2 vs 2\n"+
                        "   Cuarta acción defensiva\n"+
                        "El jugador que sume el mayor numero de puntos durante los cuatro periodos ganara la partida.\n"+
                        "*********************************** HOW TO PLAY ***********************************************\n"
        );
    }

    /**
     * Ordena la Arraylist del jugador o posición
     */
    public static void orderPosition(){
        LopezMiquel_Card aux;

        for (int i = 0; i < playersList.get(1).getDeck().size(); i++) {
            for (int j = 0; j < playersList.get(1).getDeck().size(); j++) {
                if (playersList.get(1).getDeck().get(i).getPosition()<=playersList.get(1).getDeck().get(j).getPosition()){
                    aux = playersList.get(1).getDeck().get(i);
                    playersList.get(1).getDeck().set(i, playersList.get(1).getDeck().get(j));
                    playersList.get(1).getDeck().set(j,aux);
                }
            }
        }
    }

    /**
     * Imprime el menu
     * @return Retorna la opción del menu
     */
    public static int menu(){
        int option;
        String[] options = new String[]{"Bienvenido a LaSalleNBA", "1.  Opciones del usuario",
                "2.  Abrir paquete", "3.  Jugar", "4.  Agregar un fantasy player manualmente",
                "5.  Como jugar", "6.  Cartas disponibles", "7.  Salir"};

        for (String x:options) {
            System.out.println(x);
        }
        option=numbersWithRange("Escoge una opción: ", 1, 7);

        mainDeck.clear();
        clearArray();
        addPlayer();
        readBbdds();

        return option;
    }

    /**
     * Anade una carta de forma manual
     */
    public static void addPlayerManual(){
        Scanner sc = new Scanner(System.in);
        String name, surname, team;
        int position, atk, def;

        name = onlyString("Nombre: ");
        surname = onlyString("Apellido: ");
        position = numbersWithRange("Posición: ", 1, 5);
        atk = numbersWithRange("Ataque: ", 50, 99);
        def = numbersWithRange("Defensa: ", 50, 99);
        System.out.print("Equipo: ");
        team= sc.nextLine();

        LopezMiquel_Card newPlayer = new LopezMiquel_Card(name, surname, position,atk, def, team);
        mainDeck.add(newPlayer);
    }

    /**
     * Abrir un sobre
     */
    public static void openPacket(){
        int number;
        for (int i = 0; i < 5; i++) {
            number = (int)(Math.random()*mainDeck.size());
            playersList.get(1).getStickerAlbum().add(mainDeck.get(number));
            System.out.println(mainDeck.get(number));
        }
    }

    /**
     * Las opciones que tiene el usuario
     */
    public static void userOptions(){
        int option;
        Scanner sc = new Scanner(System.in);

        System.out.println( "1.  Cambiar el nombre\n"+
                "2.  Mostrar el album de cromos");
        option=numbersWithRange("Escoge una opción: ", 1, 2);

        switch (option){
            case 1:
                System.out.print("Escoge un nuevo nombre: ");
                playersList.get(1).setNickname(sc.nextLine());
                break;
            case 2:
                for (LopezMiquel_Card x : playersList.get(1).getStickerAlbum()){
                    System.out.println(x);
                }
                break;
        }
    }

    /**
     * Hace la 4 veces la acción singleQuarter()
     */
    public static void match(){
        quarter(1);
        quarter(2);
        quarter(3);
        quarter(4);
    }

    /**
     * Se produce una ronda del juego (oneVsOneDEF(), oneVsOneATK(), twoVsTwo() y oneVsOneDEF())
     * @return int, 1 o 0 dependiendo si ha ganado
     */
    public static void quarter(int x){

        oneVsOneDEF();
        scoreboard();
        if (x!=4){
            dealOneCard(0);
            dealOneCard(1);
        }

        oneVsOneATK();
        scoreboard();
        if (x!=4){
            dealOneCard(0);
            dealOneCard(1);
        }

        twoVsTwo();
        scoreboard();
        if (x!=4){
            dealOneCard(0);
            dealOneCard(1);
        }

        oneVsOneDEF();
        scoreboard();
        if (x!=4){
            dealOneCard(0);
            dealOneCard(1);
        }

    }

    /**
     * Imprime el marcador actual
     */
    public static void scoreboard(){
        System.out.println("*********************************\n" +
                "                                 \n"+
                "      " + playersList.get(1).getNickname() + "         " + playersList.get(0).getNickname() + "      \n"+
                "        " + scoreboard[1] + "             " + scoreboard[0] + "       \n"+
                "                                 \n"+
                "*********************************\n");
    }

    /**
     * Compara el atributo def de la carta del usuario vs el atributo atk del bot
     * Asigna valor al marcador
     * @return Si el jugador ha ganado o ha perdido
     */
    public static void oneVsOneDEF(){
        System.out.println("Esta ronda defiendes");
        round(0,false);
        round(1,false);
        System.out.println(botFloor);
        System.out.println(playerFloor);
        if (playerFloor.get(0).getDef()>=botFloor.get(0).getAtk()){
            scoreboard[1]+=playerFloor.get(0).getDef();
            scoreboard[0]+=botFloor.get(0).getAtk();
            System.out.println("Has ganado esta ronda");
        }else {
            System.out.println("Has perdido esta ronda");
            scoreboard[1]+=playerFloor.get(0).getDef();
            scoreboard[0]+=botFloor.get(0).getAtk();
        }
        clearArray();
    }

    /**
     * Compara el atributo atk de la carta del usuario vs el atributo def del bot
     * Asigna valor al marcador
     * @return Si el jugador ha ganado o ha perdido
     */
    public static void oneVsOneATK(){
        System.out.println("Esta ronda atacas");
        round(0,false);
        round(1,false);
        System.out.println(botFloor);
        System.out.println(playerFloor);
        if (playerFloor.get(0).getAtk()>=botFloor.get(0).getDef()){
            System.out.println("Has ganado esta ronda");
        }else {
            System.out.println("Has perdido esta ronda");
        }
        scoreboard[1]+=playerFloor.get(0).getAtk();
        scoreboard[0]+=botFloor.get(0).getDef();
        clearArray();
    }

    /**
     * Compara el atributo ovr de la carta del usuario vs el atributo ovr del bot
     * Asigna valor al marcador
     * @return Si el jugador ha ganado o ha perdido
     */
    public static void twoVsTwo(){
        System.out.println("Ronda especial 2 vs 2");
        round(0,true);
        round(1,true);
        System.out.println(botFloor);
        System.out.println(playerFloor);

        if ((playerFloor.get(0).getOvr()+playerFloor.get(1).getOvr())>=(botFloor.get(0).getOvr()+botFloor.get(1).getOvr())){
            scoreboard[1]+=playerFloor.get(0).getOvr()+playerFloor.get(1).getOvr();
            scoreboard[0]+=botFloor.get(0).getOvr()+botFloor.get(1).getOvr();
            System.out.println("Has ganado esta ronda");
        }else {
            scoreboard[1]+=botFloor.get(0).getOvr()+botFloor.get(1).getOvr();
            scoreboard[0]+=botFloor.get(0).getOvr()+botFloor.get(1).getOvr();
            System.out.println("Has perdido esta ronda");
        }
        clearArray();
    }

    /**
     * Limpia la Array de cartas de cada jugador
     */
    public static void clearArray(){
        playerFloor.clear();
        botFloor.clear();
    }

    /**
     * Añade la carta elegida por el jugador y la añade a la array para ese turno
     * @param player Jugador que hace la acción
     * @param two Si es true es ronda es 2 vs 2 si no 1 vs 1
     */
    public static void round(int player, boolean two){
        if (player == 0){
            int number = (int)(Math.random()*playersList.get(player).getDeck().size());
            if (!two){
                botFloor.add(playersList.get(0).getDeck().get(number));
                playersList.get(0).getDeck().remove(number);
            }else{
                botFloor.add(playersList.get(0).getDeck().get(number));
                playersList.get(0).getDeck().remove(number);
                number = (int)(Math.random()*playersList.get(player).getDeck().size());
                botFloor.add(playersList.get(0).getDeck().get(number));
                playersList.get(0).getDeck().remove(number);
            }
        }else{
            if (!two){
                playerFloor.add(turn(player));
            }else{
                playerFloor.add(turn(player));
                playerFloor.add(turn(player));
            }
        }
    }

    /**
     * Elección de la carta y comprueba si la tienes en el deck
     * @param player Player que hace la acción
     * @return Retorna la carta si la tienes en el deck
     */
    public static LopezMiquel_Card turn(int player){
        LopezMiquel_Card election = new LopezMiquel_Card("default", "default", 0, 0, 0, "default", "default");
        String card;
        Scanner sc = new Scanner(System.in);
        boolean correctElection=false;

        System.out.println("*****************************************************************");
        for (LopezMiquel_Card c:playersList.get(player).getDeck()) {
            System.out.println(c);
        }
        System.out.println("*****************************************************************");

        do {
            System.out.print("Nombre del jugador: ");
            card = sc.nextLine();
            election.setName(card);
            int same=0, count=0;
            String subCard="";


            for (int cardOrder = 0; cardOrder < playersList.get(player).getDeck().size(); cardOrder++) {
                if (playersList.get(player).getDeck().get(cardOrder).getName().equalsIgnoreCase(card)){
                    same=sameName();
                    if (same==1){
                        if(count==0){
                            System.out.print("Apellido del jugador: ");
                            subCard=sc.nextLine();
                            count++;
                        }
                        if (playersList.get(player).getDeck().get(cardOrder).getName().equalsIgnoreCase(card) && playersList.get(player).getDeck().get(cardOrder).getSurname().equalsIgnoreCase(subCard)){
                            election = playersList.get(player).getDeck().get(cardOrder);
                            playersList.get(player).getDeck().remove(cardOrder);
                            correctElection=true;
                            break;
                        }
                    }else{
                        election = playersList.get(player).getDeck().get(cardOrder);
                        playersList.get(player).getDeck().remove(cardOrder);
                        correctElection=true;
                        break;
                    }
                }
            }
            if (!correctElection){
                System.out.println(RED + "Carta no encontrada" + RESET);
            }
        }while (!correctElection);

        return election;
    }

    /**
     * Comprueba si en las 5 cartas que tienes en la mano hay dos con el mismo nombre
     * @return 1 si hay 2 o mas con el mismo nombre
     */
    public static int sameName(){
        int same=0;

        for (int i = 0; i < playersList.get(1).getDeck().size(); i++) {
            for (int j = 0; j < playersList.get(1).getDeck().size(); j++) {
                if(playersList.get(1).getDeck().get(i).getName().equalsIgnoreCase(playersList.get(1).getDeck().get(j).getName())){
                    same++;
                }
            }
        }
        if (same>5){
            return 1;
        }else{
            return 2;
        }
    }

    /**
     * Distribuye 5 cartas a cada jugador
     * @param x Jugador al que asignar las cartas
     */
    public static void dealCards(int x){
        int number = (int)(Math.random()*mainDeck.size());
        boolean flag=false;
        clearArray();

        playersList.get(x).addCardToDeck(mainDeck.get(number));
        mainDeck.remove(number);
        do {
            number = (int)(Math.random()*mainDeck.size());
            playersList.get(x).addCardToDeck(mainDeck.get(number));
            for (int i = 0; i < playersList.get(x).getDeck().size()-1; i++) {
                if (playersList.get(x).getDeck().get(i).getPosition()==playersList.get(x).getDeck().get(playersList.get(x).getDeck().size()-1).getPosition()){
                    playersList.get(x).getDeck().remove(playersList.get(x).getDeck().size()-1);
                    flag=true;
                    break;
                }
            }
            if (!flag){
                mainDeck.remove(number);
            }
            flag=false;
        }while (playersList.get(x).getDeck().size()<5);
        orderPosition();
    }

    /**
     * Reparte una casta y comprueba que no haya de esa posicion
     * @param x jugador al que repartir
     */
    public static void dealOneCard(int x){
        boolean flag=false;
        do {
            int number = (int)(Math.random()*mainDeck.size());
            playersList.get(x).addCardToDeck(mainDeck.get(number));
            for (int i = 0; i < playersList.get(x).getDeck().size()-1; i++) {
                if (playersList.get(x).getDeck().get(i).getPosition()==playersList.get(x).getDeck().get(playersList.get(x).getDeck().size()-1).getPosition()){
                    playersList.get(x).getDeck().remove(playersList.get(x).getDeck().size()-1);
                    flag=true;
                    break;
                }
            }
            if (!flag){
                mainDeck.remove(number);
            }
            flag=false;
        }while (playersList.get(x).getDeck().size()<5);
        orderPosition();
    }

    /**
     * Control de errores, leer un int con un rango
     * @param input Prgeunta al usuario
     * @param valorMin Valor mínimo del rango
     * @param valorMax Valor máximo del rango
     * @return Valor de la pregunta al usuario
     */
    public static int numbersWithRange(String input, int valorMin, int valorMax){
        Scanner sc = new Scanner(System.in);
        int x = 0;
        boolean correctValue;

        do{
            System.out.print(input);
            correctValue = sc.hasNextInt();

            if(!correctValue){
                System.out.println(RED+"ERROR: No has escrito un numero"+RESET);
                sc.nextLine();
            }else{
                x = sc.nextInt();
                sc.nextLine();

                if (x < valorMin || x > valorMax){
                    System.out.println(RED+"ERROR: El numero esta fuera del rango"+RESET);
                    correctValue = false;
                }
            }
        }while(!correctValue);
        return x;
    }

    /**
     * Añadir jugadores
     */
    public static void addPlayer(){
        LopezMiquel_Player pc = new LopezMiquel_Player("Bot");
        playersList.add(pc);
        LopezMiquel_Player miquel = new LopezMiquel_Player("miqueel97");
        playersList.add(miquel);
    }

    /**
     * Solo letras
     * @param input Pregunta
     * @return String
     */
    public static String onlyString(String input){
        Scanner sc = new Scanner(System.in);
        String x;
        boolean correctValue=false;

        do {
            System.out.print(input);
            x=sc.nextLine();

            try {
                Integer.parseInt(x);
                System.out.println(RED + "Nomes pots escriure lletres." + RESET);
            } catch (NumberFormatException excepcion) {
                correctValue=true;
            }
        }while(!correctValue);

        return  x;
    }

}