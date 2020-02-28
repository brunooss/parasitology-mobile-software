package com.android.parasitologymobilesoftware;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.webkit.WebView;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.auth.User;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int progressStatus;
    private ProgressBar progressBar;
    private Button buttonAlert;
    boolean alert = true;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore dataBase;

    private String email;
    private String schoolGrade;
    private String completeName;

    private ConstraintLayout constraintLayoutStudentFirst;
    private ConstraintLayout constraintLayoutStudentSecond;

    private View dialogView;

    private AlertDialog alertDialogFeedback;

    public String searchSubcategories[][] = {
            {"Protozoários", "Doença de Chagas", "Tricomonose", "Balantidíase","Malária", "Giardíase", "Leishmanioses"},
            {"Helmintos", "Esquistossomose mansoni", "Teníase", "Cisticercose", "Ascaridíase", "Enterobíase", "Hidatidose"},
            {"Artrópodes", "Hemípteros", "Mosquitos", "Moscas", "Piolhos", "Pulgas", "Ácaros"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();
        dataBase = FirebaseFirestore.getInstance();

        email = firebaseAuth.getCurrentUser().getEmail();
        completeName = firebaseAuth.getCurrentUser().getDisplayName();

        DocumentReference docRef = dataBase.collection("generalUserInfo").document(email);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                setSchoolGrade(documentSnapshot.get("school grade").toString());
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.viewPagerHome);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new HomeTabsAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1);

        TabLayout tabLayout = findViewById(R.id.tabLayoutHome);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setElevation(2);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_person_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_home_white_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_book_white_24dp);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setElevation(5);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        dialogView = getLayoutInflater().inflate(R.layout.dialog_feedback, null);

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialogFeedback = builder.create();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        final ListView listView = findViewById(R.id.listViewHome);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();

                Intent intent = new Intent(getBaseContext(), SubjectActivity.class);
                if (selected.contains("Protozoários"))
                    intent.putExtra("index", "protozoarios.html");
                else if (selected.contains("Helmintos"))
                    intent.putExtra("index", "helmintos.html");
                else if (selected.contains("Artrópodes"))
                    intent.putExtra("index", "artropodes.html");

                startActivity(intent);
            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s != null && !s.isEmpty()) {
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1);
                    for (String[] searchSubcategory : searchSubcategories) {
                        for (int j = 1; j <= searchSubcategory.length - 1; j++) {
                            if (searchSubcategory[j].toLowerCase().contains(s.toLowerCase()))
                                stringArrayAdapter.add(searchSubcategory[j] + "\nEm " + searchSubcategory[0]);
                        }
                    }
                    listView.setVisibility(View.VISIBLE);
                    listView.setAdapter(stringArrayAdapter);
                }
                else {
                    return false;
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                listView.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
            // Open the AboutActivity //@Todo
        } else if (id == R.id.nav_feedback) {
            alertDialogFeedback.show();
        } else if (id == R.id.nav_out) {
            Toast.makeText(this, "Aguarde, saindo de sua conta.", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    System.exit(0);
                }
            }, 1000);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onSignOutButtonClick(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SigninActivity.class));
        finish();
    }

    public void onSetAlertButtonClick(View view) {
        buttonAlert = (Button) findViewById(R.id.buttonStudentPreferencesSetAlert);
        if (alert == true) {
            buttonAlert.setBackground(getResources().getDrawable(R.drawable.custom_linear_layout_alert_false, getTheme()));
            alert = false;
        } else {
            buttonAlert.setBackground(getResources().getDrawable(R.drawable.custom_linear_layout_alert_true, getTheme()));
            alert = true;
        }

//        final Calendar calendar = Calendar.getInstance();
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//
//        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.PickerTheme, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int i, int i1) {
//
//            }
//        }, hour, minute, true);
//        timePickerDialog.show();
    }

    public void onSetDateButtonClick(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.PickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void onCategoryButtonClick(View view) {
        Intent intent = new Intent(this, SubjectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", view.getId());
        if(getResources().getResourceEntryName(view.getId()).contains("Artropodes"))
            bundle.putString("index", "helmintos.html");
        else if(getResources().getResourceEntryName(view.getId()).contains("ChapterIV"))
            bundle.putString("index", "artropodes.html");
        else if(getResources().getResourceEntryName(view.getId()).contains("Helmintos"))
            bundle.putString("index", "protozoarios.html");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void settingProgress(int progressToGo){                      // Call this function to change the progress bar, passing an int variable that goes from 0 to 100
        DocumentReference docRef = dataBase.collection("generalUserInfo").document(email);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                setProgressStatus(documentSnapshot.getLong("progress status").intValue());
            }
        });
        progressStatus += progressToGo;         // Old Progress (progressStatus) + New Progress (progressToGo) = progressStatus
        if (progressStatus <= 100) {
            progressBar = findViewById(R.id.progressApp);
            progressBar.setProgress(progressStatus, true);

            /* Updating the database with */
            Map<String, Object> updateProgress = new HashMap<>();
            updateProgress.put("progress status", progressStatus);

            /* Updating the progress status in the both collections's kind */
            dataBase.collection("generalUserInfo").document(email)
                    .update(updateProgress);
            dataBase.collection(schoolGrade).document(completeName)
                    .update(updateProgress);
        }
    }

    public void onButtonReview(View view){
        settingProgress(10);
    }

    {
        /// PROTOZOARIOS

/*
<html>
    <title>
        Protozoários
    </title>
    <body>
        <style>
            * {
                text-align: justify;
            }
            .div-placeholder {
                height: 0px;
            }
        </style>

        <div class="div-placeholder">

        </div>

        <h1>1. Protozoários.</h1>

        <h2>1.1. Malária</h2>

        <h3>A malária é uma doença causada por protozoários do gênero <i><b>Plasmodium</b></i> (<i>P. falciparum</i>, <i>P. vivax</i> e <i>P. malariae</i>), os quais pertencem ao filo <i>Apicomplexa</i> e à família <i>Plasmodiidae</i>. Tal doença, apesar de quase erradicada na década de 1950, ainda é um grave problema para a saúde mundial, uma vez que são registrados, anualmente, cerca de 500 mil novos casos, existindo por volta de 200 milhões de pessoas infectadas no mundo. Outro problema é a alta taxa de mortalidade que a doença causa, com um valor aproximado de dois milhões de mortes por ano, sendo a doença parasitária que mais mata no mundo.
            Essa parasitose ocorria em praticamente todos os continentes, mas foi erradicada nos países desenvolvidos e hoje está restrita à América Latina, à África, à Ásia e à parte insular da Oceania. Esse fato comprova a urgência em investimentos nas áreas de saúde e educação sanitária nos países em desenvolvimento, uma vez que, unidas ao combate ao vetor e ao tratamento dos indivíduos já infectados, são as principais formas de profilaxia da doença. No Brasil, é uma endemia que acomete a região amazônica.
            O ciclo de vida do plasmódio é heteroxênico, com reprodução sexuada ocorrendo no mosquito do gênero Anopheles e reprodução assexuada, no humano. A figura abaixo representa o ciclo de vida de um <i>Plasmodium</i>:
            *****************FIGURA
        </h3>

        <h3> O diagnóstico da doença é feito por meio de hemoscopia, processo no qual faz-se um esfregaço com uma gota de sangue do paciente e analisada a presença ou não dos protozoários. É recomendado que esse método seja feito na fase inicial, já que há maior quantidade de plasmódios no sangue periférico.
            Os sintomas gerais da malária são anemia, por conta da destruição das hemácias, o paroxismo malárico, no qual o doente passa por alternâncias de frio e calor seguidas de sudorese, os episódios de febre com intervalos de cerca de 72 horas (febre terçã), podendo existir complicações maiores como insuficiência renal e lesões no cérebro.
            Existem três mecanismos capazes de proteger um indivíduo da malária: a resistência adquirida, a imunidade inata e a resistência inata. O primeiro ocorre pela atuação do parasito no doente, com a estimulação do sistema imunitário capaz de eliminar a infecção ainda em seu começo ou mesmo reduzir a carga parasitária, diminuindo a letalidade da doença. Esse tipo de resposta é comum em áreas onde a doença é endêmica. No segundo, o indivíduo não desenvolve a doença ainda que esteja infectado devido a uma resposta imune que envolve receptores da membrana plasmática de macrófagos. O terceiro caso é o que a resistência independe de um contato anterior com o plasmódio. Essa situação ocorre em crianças com anemia falciforme e pessoas que não apresentam determinados antígenos.
            O tratamento dos doentes é de fundamental importância para a profilaxia dessa parasitose e ocorre por meio de medicamentos antimaláricos que podem ser usados individualmente ou combinados administrados de acordo com a espécie do plasmódio, com a gravidade da doença, com a história de exposição anterior à infecção e com a idade do paciente. Alguns desses remédios são cloroquina, primaquina (usados para <i>P. vivax</i>, <i>P. ovale</i> e <i>P. malariae</i>), artemeter, lumefantrina, artesunato e mefloquina (<i>P. falciparum</i>).

        </h3>


        <h2>1.2. Giardíase</h2>

        <h3>A giardíase é uma parasitose causada pelo protozoário <i><b>Giardia duodenalis</b></i>, também chamado de <i><b>Giardia lamblia</b></i> ou <i><b>G. intestinalis</b></i>. Faz parte do filo <i>Sarcomastigophora</i>, subfilo <i>Mastigophora</i> e família <i>Hexamitidae</i>. Essa espécie é a única de seu gênero capaz de parasitar seres humanos, sendo que as outras causam doenças em outros mamíferos, aves, répteis e anfíbios.
            Apresenta dois estágios de desenvolvimento: o de cisto e o de trofozoíto. No primeiro possui formato oval e é envolvido por uma parede de glicoproteína que torna o cisto mais resistente a variações de umidade, temperatura e produtos químicos para desinfecção de ambientes. O segundo apresenta quatro pares de flagelos, formato piriforme, além de simetria bilateral.
            Seu ciclo é monoxeno e inicia-se com a ingestão dos cistos pelo homem. Após isso, acontece o processo de desencistamento com a acidez do estômago. Cada cisto libera uma forma chamada de excitozoíto, o qual se multiplicará formando quatro trofozoítos binucleados que realizarão divisão binária de modo a colonizar o intestino. O parasito completará seu ciclo na porção final do intestino, o íleo, onde realiza encistamento e será liberado nas fezes, podendo permanecer nessa forma por meses no ambiente.
            A prevalência dessa parasitose é duvidosa, uma vez que muitos dos doentes possuem um quadro assintomático e em vários casos nos quais há sintomas, esses são confundidos com os de uma virose. Os principais sintomas são diarreia, perda de peso, má absorção de nutrientes como gordura, vitaminas lipossolúveis, ferro e lactose. No caso da infecção em indivíduos adultos, essas deficiências não causam danos significativos, no entanto, podem atrapalhar seriamente o desenvolvimento cognitivo e físico de crianças.
            O diagnóstico pode ser clínico, em que são analisados os sintomas, laboratorial do tipo parasitológico, com o exame microscópico de fezes, ou laboratorial do tipo imunológico, usando-se o método ELISA.
            Apesar de ser um mecanismo não totalmente compreendido, sabe-se que o corpo humano é capaz de produzir resposta imune para as infecções por <i>Giardia</i>. A resposta específica ocorre no soro, em que anticorpos do tipo IgG, IgM e IgA atuam, e na mucosa intestinal, ao que IgA é capaz de reconhecer proteínas dos trofozoítos e diminuir a possibilidade de sua adesão às células do epitélio intestinal. Além disso, mecanismos como barreiras naturais existentes no intestino delgado participam no controle da infecção.
            Para o controle da doença, faz-se necessária a atuação do poder público para que a população possua acesso a saneamento básico e água potável. Além disso, o tratamento dos doentes, a higiene pessoal e a educação sanitária são importantes medidas. Já o tratamento da doença é normalmente feito com o medicamento metronidazol, que elimina a infecção em cerca de 80 a 95% dos pacientes.
        </h3>

        <h2>1.3. Leishmaniose</h2>

        <h3>A leishmaniose é uma doença causada por protozoários do filo <i>Sarcomastigophora</i>, subfilo <i>Mastigophora</i>, família <i>Trypanosomatidae</i> e gênero <i>Leishmania</i>. Nas Américas, os vetores dessa protozoose são pertencentes ao gênero <i>Lutzomyia</i>. Há outros vetores nos demais continentes, como os do gênero <i>Phlebotominae</i> na Europa.
            A Leishmania possui duas formas básicas: amastigota, localizada dentro dos macrófagos dos órgãos alvos, e promastigota, forma infectante encontrada nos insetos vetores, flebótomos do gênero <i>Lutzomyia</i>. A amastigota mede aproximadamente de 2 a 5 µm de diâmetro e não possui flagelo externo, enquanto a promastigota é alongada, com aproximadamente 20µm de comprimento, comum flagelo livre, imergindo do cinetoplasto. Ambas as formas têm em seu interior um núcleo e um cinetoplasto, formado por DNA mitocondrial.
            O inseto vetor flebotomíneo infecta-se picando algum animal infectado (reservatório) ao ingerir as formas amastigotas de sua pele. No tubo digestivo do inseto, se transformam em promastigotas, que se reproduzem intensamente através de divisão binária (cissiparidade). Essas formas promastigotas migram para o aparelho bucal do inseto, que ao se alimentar de sangue em um novo hospedeiro, um humano ou animal, inoculam na sua pele essas formas promastigotas. Finalmente, essas invadem os macrófagos locais e tornam-se amastigotas, que se multiplicam por divisão binária até romperem o macrófago. Livres nos tecidos ou no sangue, são fagocitadas por outros macrófagos, reiniciando o processo proliferativo.
            A profilaxia da leishmaniose é considerada difícil. No meio silvestre, o uso de inseticidas contra flebotomíneos não é recomendado uma vez que prejudicaria insetos polinizadores, mas seu uso no ambiente domiciliar e peridomiciliar é aprovado. O uso de repelentes para humanos e de coleiras repelentes em cães, a vacinação desses animais, o tratamento das pessoas doentes, a eliminação de possíveis criadouros e a educação da população são, também, medidas a serem tomadas para o controle das leishmanioses.
            Para o tratamento da parasitose em humanos, o medicamento mais comum a ser utilizado é o Glucantime, mas em casos de pessoas imunossuprimidas, com insuficiência cardíaca, hepática ou renal, gestantes, crianças com menos de um ano e pessoas que não responderam bem ao tratamento com Glucantime a anfotericina B lipossomal é usada. Em cães o medicamento adequado é o Milteforan.
            Estudos demonstram que a <i>Leishmania</i> tem grande capacidade de escapar de defesas inespecíficas. Outro fato importante é a deficiência dos macrófagos em destruir as formas amastigotas do protozoário, tornando-se um estímulo para o parasitismo. Há atuação dos linfócitos T helper, os quais liberam substâncias para a supressão dos parasitos, e aumento das taxas de anticorpos, em especial, os IgG.
            São várias as espécies do gênero <i>Leishmania</i> que acometem o ser humano, podendo causar duas formas distintas da parasitose: a tegumentar e a visceral.
            <ul>
                <li>
                    <h2>Leishmaniose visceral</h2>
                    <h3>
                        Os casos de calazar no Brasil têm aumentado muito, tornando-se uma grave questão de saúde pública. Em toda a América, a espécie causadora da doença é a <i><b>L. infantum chagasi</b></i>.
                        No diagnóstico do tipo parasitológico para a leishmaniose visceral é feita uma punção da medula óssea do osso esterno e com o material obtido pode ser feito um esfregaço, conforme descrito anteriormente para a leishmaniose tegumentar, ou semear esse material em meios de cultura específicos, como NNN e LIT. Já o diagnóstico imunológico pode ser feito com reação de ELISA, TraLd (teste rápido anticorpo anti <i>Leishmania donovani</i>), reação de imunofluorescência indireta, feitas com o plasma sanguíneo. Há ainda a utilização das técnicas de análise de DNA, como a PCR (Reação em cadeia da Polimerase), capazes de indicar com grande precisão o agente invasor do organismo.
                        No caso do calazar canino pode ser feito um esfregaço com pele da orelha ou punção da medula óssea no método parasitológico, enquanto que no imunológico o TraLd, a PCR e a imunofluorescência indireta também são usados.
                        Os sintomas dessa doença envolvem hepatomegalia (aumento do volume do fígado), esplenomegalia (aumento do volume do baço), disfunção na medula óssea acompanhada de anemia que pode ser fatal.
                    </h3>
                </li>
                <li>
                    <h2><i>Leishmaniose Tegumentar</i></h2>
                    <h3>
                        A L.T.A. é uma doença silvestre, relativamente estável em termos de incidência e prevalência, com raros surtos em áreas de desbravamento de vegetação. As espécies que causam essa parasitose nas Américas são <i><b>L. brasilienses</b></i>, <i><b>L. amazonenses</b></i>, <i><b>L. mexicana</b></i> e <i><b>L. guyanensis</b></i>.
                        O diagnóstico parasitológico da leishmaniose tegumentar é realizado através de uma biópsia da lesão ou ferida. Do pequeno fragmento retirado, é feito um esfregaço em lâmina de microscopia ou, após escarificar a borda da lesão, pode-se, também, comprimir uma lamina de microscopia sobre a área escarificada, obtendo-se um esfregaço por aposição. O material colhido pode ser corado pelo método de Giemsa ou pelo método panótico rápido. Com o material da biópsia também poderão ser feitas culturas de <i>Leishmania</i>, com meios de cultura específicos, como NNN e LIT.
                        Pode ser realizado também o diagnóstico imunológico em que, a intradermorreação de Montenegro, associada à suspeita clínico-epidemiológica, é o método mais utilizado, com injeção intradérmica de 0,1 mL do antígeno na face interna do antebraço e leitura de 49 a 72 horas após. A positividade do teste é determinada pelo aumento da pápula previamente formada.
                        Os protozoários na forma amastigota permanecem na pele, de modo a formar um nódulo que se transformará em uma ferida. Essa ferida pode se espalhar para a pele de outras partes do corpo, podendo atingir o rosto.

                    </h3>
                </li>
            </ul>
        </h3>
    </body>
</html>
 */

/// ARTROPODES

/*
<html>
    <title>
        Artrópodes
    </title>
    <body>
        <style>
        * {
            text-align: justify;
        }
        .div-placeholder {
            height: 0px;
        }
        </style>

        <div class="div-placeholder">

        </div>

        <h1>1. Artrópodes.</h1>

        <h2>1.1. Escabiose humana</h2>

        <h3>A escabiose humana (sarna) é uma doença causada pelo ectoparasito <i><b>Sarcoptes scabiei</b></i>, artrópode pertencente à classe dos aracnídeos e família <i><b>Sarcoptidae</b></i>. É a única espécie de seu grupo cujo parasitismo ao ser humano não é acidental.
            O ácaro penetra a pele humana deixando lesões em formato de galerias, em um trajeto linear. Causa espessamento na pele, coceira, prurido principalmente nas regiões de dedos, pulsos, cotovelos, axilas, joelhos e em volta da cintura. Esses parasitos se alimentam da epiderme de seu hospedeiro e causam graves reações alérgicas geralmente no horário da noite.
            Sua transmissão pode se dar através de contato direto, sexual, por compartilhamento de objetos e contato com objetos de uso comum. Faz- se necessário, para o controle dessa doença, portanto, a educação em saúde para a população, boa higiene pessoal e o tratamento dos doentes, o qual ocorre pelos medicamentos Ivermectina e Benzoato de benzila.
            Essa doença é de ampla ocorrência em áreas carentes, nas quais os cuidados com a higiene são menores, bem como a aceitação e aderência ao tratamento. A figura abaixo ilustra como ocorre o ciclo da doença.
            O diagnóstico da escabiose pode ser feito por meio de exames clínicos ou parasitológicos. Com o primeiro, faz-se a anamnese, dando importância a fatos como histórico de contágio de pessoas próximas e queixas de prurido, e é observado o aspecto, o tamanho e localização das lesões. Para o segundo, existem dois tipos de exame: fita gomada, em que se coloca uma fita sobre as crostas que é depois analisada em microscópio, e raspado de pele, no qual a pele do limite das lesões é raspada e também analisada em microscópio.
            Há indivíduos resistentes à infecção pelo ácaro e esse fato está relacionado ao mecanismo de defesa que o indivíduo utiliza. Quando há maior suscetibilidade, a resposta imune se basei na produção de imunoglobulina do tipo IgE. Quando há resistência, a resposta imune é provocada pela ação de IFN-γ, uma citocina capaz de recrutar leucócitos e macrófagos.
        </h3>


        <h2>1.2. <i>Anopheles sp.</i></h2>

        <h3>A malária, grande flagelo da humanidade, é transmitida por mosquitos do gênero <i><b>Anopheles</b></i> entre eles o <i>A. gambiae</i>, <i>A. darlingi</i>, <i>A. maculipennis</i>, <i>A. funestus</i> e <i>A. albimanus</i>. Esses mosquitos são pertencentes ao filo dos artrópodes, classe <i>insecta</i> e família <i>Culicidae</i>, a mesma de outros vetores como os dos gêneros Aedes, Culex, Haemagogus e Sabethes. Tem distribuição mundial, sendo que, no Brasil, é prevalente na região amazônica e fronteiriça, apesar de existirem distintas espécies ao longo de todo o país
            Esse vetor, em sua fase adulta possui entre 3 e 6 mm, pernas longas, tórax, pernas, asas e abdome revestidos por escamas. Seu ciclo de vida consiste em fases aquáticas, imaturas, como ovo, larva e pupa, e uma fase terrestre, adulta.
            Um fato interessante a ser ressaltado é que todas as fêmeas de mosquitos de importância parasitológica são hematófagas obrigatórias e costuma picar no período da noite. As peças bucais desse vetor são do tipo perfurante e sugadora e penetram o interior dos vasos capilares, lançando neles vasodilatadores, a fim de sugar o sangue por meio da ação coordenada de bombas e da pressão negativa produzida.
            No Brasil, a malária tem apresentado diversos novos focos, sendo o A. darlingi o principal agente. Isso se deve ao fato de ele conseguir adaptar-se bem a regiões desmatadas e onde ocorre o garimpo. Torna-se necessário, portanto, que medidas preventivas sejam tomadas a exemplo da eliminação de focos do mosquito, maior controle por parte dos órgãos públicos quanto à formação de novas áreas de garimpo e desmatamento e o uso de repelentes para as pessoas que vivem em regiões endêmicas.
        </h3>

        <h2>1.3. <i>Lutzomyia sp.</i></h2>

        <h3>Pertencentes ao filo dos artrópodes, à família <i>Psychodidae</i> e à subfamília dos flebotomíneos, insetos do gênero <i><b>Lutzomyia</b></i> apresentam importância médica e veterinária por serem vetores, na região das Américas, dos protozoários causadores da leishmaniose. No velho mundo, os transmissores dessa doença são do gênero Phlebotomus.
            Os Lutzomyia sp. medem cerca de 2 a 4 mm, possuem corpo revestido de cerdas e asas sempre levantadas. As fêmeas costumam picar aves e mamíferos no período noturno e crepuscular, já os machos não são hematófagos e se alimentam principalmente de sucos vegetais. A picada é rápida e dolorosa.
            A principal espécie desse vetor é <i><b>Lutzomyia longipalpis</b></i>, mas existem também as espécies <i>L. whitmani</i>, <i>L. intermedia</i>, <i>L. flaviscutelata</i>, <i>L. pessoai</i>, entre outras.
            Seu ciclo biológico inclui quatro etapas: ovo, o qual é elíptico, alongado; larva, vermiforme e que apresenta pseudópodos; pupas, de formato cilíndrico e que mede cerca de 2 mm; e adulto.
            O controlo desse inseto é muito complexo pois seus ovos são depositados de maneira dispersa em meio à matéria orgânica e sua maioria é silvestre. Porém, existem medidas profiláticas que devem ser tomadas para evitar a infecção de humanos e animais doméstico pela leishmaniose. Entre essas medidas estão o uso de inseticidas na região domiciliar e peridomiciliar, a telagem de portas e janelas, o uso de repelentes em humanos e de coleiras com repelentes em cães, a vacinação desses animais, a limpeza de quintais e praças, de modo a reduzir os focos do vetor. Outra importante medida é a educação sanitária da população, para que possam se prevenir da doença e procurar ajuda ao reconhecer seus sintomas. A aplicação de inseticidas em ambientes silvestres, no entanto não deve ser realizada.
        </h3>

        <h2>1.4. Pulgas</h2>

        <h3>As pulgas são ectoparasitas temporários, de corpo achatado lateralmente e que medem cerca de 1 a 3 mm. São artrópodes da classe <i>insecta</i>, ordem <i>Siphonaptera</i> e famílias diversas como a <i>Pulicidae</i> e a <i>Tungidae</i>.
            O ciclo de vida das pulgas é holometábolo, ou seja, passam pelos estágios de ovo, larva, pupa e adulto. As larvas se alimentam de sangue e detritos nas fezes dos indivíduos adultos, os quais são hematófagos.
            Estão distribuídas por todo o planeta e são encontradas parasitando animais e seres humanos. Algumas espécies também são vetores de bactérias e vírus, fato que torna as pulgas ainda mais perigosas. Sua picada é incômoda e é capaz de causar dermatite alérgica grave.
            Esses insetos podem ser transmitidos pelo contato direto com pessoas ou animais infectados, bem como pelo compartilhamento de objetos de uso comum, como cadeiras de cinema e sofás, ou pessoal.
            O uso de inseticidas é recomendado para o controle desses parasitas, bem como o uso de aspiradores de pó, incinerando-se o pó recolhido.
            Algumas das principais espécies são <i>Pulex irritans</i> e <i>Xenopsylla cheopis</i>, da família <i>Pulicidae</i>, e <i>Tunga penetrans</i> da família <i>Tungidae</i>.
            <ul>
                <li>
                    <h2><i>Pulex irritans</i></h2>
                    <h3>
                        A pulga <i><b>Pulex irritans</b></i> é a que mais acomete o ser humano e também é ectoparasita de outros animais. Sua picada é capaz de causar uma reação de dermatite alérgica grave. É comum em áreas malcuidadas como casas e cinemas antigos. Sua distribuição é mundial e pode ser transmissora da chamada “Tênia anã”, a <i><b>Hymenolepis nana</b></i>.
                    </h3>
                </li>
                <li>
                    <h2><i>Xenopsylla cheopis</i></h2>
                    <h3>
                        Conhecida como “pulga dos ratos”, a <i><b>Xenopsylla cheopis</b></i> possui distribuição mundial e é de importância significativa por ser transmissora da bactéria <i><b>Yersinia pestis</b></i>, causadora da peste bubônica, doença que dizimou 1/3 da população europeia na Idade Média. Em seu ciclo de vida, a pulga parasita ratos até que eles morram para que então procurem um novo hospedeiro que poderá ser um humano. Atualmente, essa bactéria está restrita a regiões silvestres, sendo alvo de permanente vigilância epidemiológica.
                        A <i>X. cheopis</i> tem grande prevalência no Brasil, bem como outra espécie pertencente ao mesmo gênero, a <i>X. brasilienses</i>.
                    </h3>
                </li>
                <li>
                    <h2><i>Tunga penetrans</i></h2>
                    <h3>
                        Essa pulga, que é parasita de homens e outros animais como cães, gatos e suínos, é popularmente conhecida como “bicho de pé” pois penetra nos pés, região em que a fêmea realiza a oviposição. Sua saliva causa um prurido característico para sua identificação.
                        Uma tumoração clara no local da picada e de estabelecimento do ectoparasito pode surgir com os dez primeiros dias. Para que seja retirada, faz-se necessária a aplicação de um bacteriostato local e o uso de uma agulha esterilizada. O buraco deixado deve ser tratado com antissépticos como álcool iodado ou mertiolato.
                        A <i>T. penetrans</i> tem importância também por ser capaz de atuar como vetor mecânico para as bactérias <i>Clostridium tetani</i> e <i>Clostridium perfrigens</i>, causadoras do tétano e da gangrena gasosa respectivamente, e para fungos que podem causar micoses.

                    </h3>
                </li>
            </ul>
        </h3>

        <h2>1.5. Pulssssssssgas</h2>

        <h3>Fusce tincidunt sapien vitae mauris rutrum sodales. Aliquam cursus eros ut diam ullamcorper ultricies.
            Pellentesque fermentum luctus est at blandit. Phasellus ultrices euismod ultricies.
            Integer et lorem sit amet dolor pulvinar suscipit eget nec est. Suspendisse nec viverra purus. Donec consectetur elementum aliquam.
            Integer id quam placerat, porttitor lacus tristique, malesuada tellus. Nulla turpis velit, placerat non pretium at, ultricies a lacus. </h3>

        <h2>1.6. Ácaros</h2>

        <h3>Fusce tincidunt sapien vitae mauris rutrum sodales. Aliquam cursus eros ut diam ullamcorper ultricies.
            Pellentesque fermentum luctus est at blandit. Phasellus ultrices euismod ultricies.
            Integer et lorem sit amet dolor pulvinar suscipit eget nec est. Suspendisse nec viverra purus. Donec consectetur elementum aliquam.
            Integer id quam placerat, porttitor lacus tristique, malesuada tellus. Nulla turpis velit, placerat non pretium at, ultricies a lacus.</h3>
    </body>
</html>
 */
    }

    public void setProgressStatus(int progressStatus){
        this.progressStatus = progressStatus;
    }

    public void setSchoolGrade(String schoolGrade){
        this.schoolGrade = schoolGrade;
    }
}


