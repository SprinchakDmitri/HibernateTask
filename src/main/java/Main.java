import com.company.hibernateproj.HibernateSessionFactoryManager;
import com.company.hibernateproj.dao.DisciplineDao;
import com.company.hibernateproj.dao.RoleDao;
import com.company.hibernateproj.dao.TaskDao;
import com.company.hibernateproj.dao.UserDao;
import com.company.hibernateproj.entity.Discipline;
import com.company.hibernateproj.entity.Role;
import com.company.hibernateproj.entity.Task;
import com.company.hibernateproj.entity.User;
import com.company.hibernateproj.enums.DisciplinesEnum;
import com.company.hibernateproj.enums.Status;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


import static com.company.hibernateproj.enums.DisciplinesEnum.*;
import static com.company.hibernateproj.enums.Status.*;

public class Main {
    private static RoleDao roleDao = new RoleDao();
    private static TaskDao taskDao = new TaskDao();
    public static DisciplineDao disciplineDao = new DisciplineDao();
    public static UserDao userDao = new UserDao();

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateSessionFactoryManager.getSessionFactory();




        List<Role> roles = rolesFactory();
        roleDao.toDatabase(roles);

        List<Task> tasks = tasksFactory();
        taskDao.toDatabase(tasks);

        List<Discipline> disciplines = disciplinesFactory();
        disciplineDao.toDatabase(disciplines);

        List<User> users = usersAMFactory(disciplines.get(1),roles, tasks);
        userDao.toDatabase(users);

        List<User> usersUpdates = usersDevFactory(disciplines.get(2),roles, tasks);
        userDao.toDatabase(usersUpdates);

        List<Role> roles1 = new ArrayList<>();
        roles1.add(roles.get(2));

        List<Task> tasks1 = new ArrayList<>();
        tasks1.add(tasks.get(0));

        List<Task> tasks2 = new ArrayList<>();
        tasks2.add(tasks.get(1));

        List<Task> tasks3 = new ArrayList<>();
        tasks3.add(tasks.get(2));

        List<Task> tasks4 = new ArrayList<>();
        tasks4.add(tasks.get(3));

        List<Task> tasks5 = new ArrayList<>();
        tasks5.add(tasks.get(4));

        userDao.updateUser(users.get(0),tasks1,roles1, disciplines.get(2));
        userDao.updateUser(users.get(1),tasks2,roles1, disciplines.get(2));
        userDao.updateUser(users.get(2),tasks3,roles1, disciplines.get(1));
        userDao.updateUser(users.get(3),tasks4,roles1, disciplines.get(3));
        userDao.updateUser(users.get(4),tasks5,roles1, disciplines.get(0));


        disciplines.get(1).setMembers((Collection)Arrays.asList(usersAMFactory(
                        disciplines.get(1), roles.stream().filter(it -> it.getRoleName().equals("Employee") ||
                        it.getRoleName().equals("Intern")).collect(Collectors.toList())
                , tasks)));
        disciplineDao.updateDiscipline(disciplines.get(1));

        disciplines.get(2).setMembers((Collection)Arrays.asList(usersDevFactory(
                disciplines.get(2), roles.stream().filter(it -> it.getRoleName().equals("Employee") ||
                        it.getRoleName().equals("Intern")).collect(Collectors.toList())
                , tasks)));


        disciplineDao.updateDiscipline(disciplines.get(1));


        List<User> headUsers = headUsersFactory(disciplines,roles);
        userDao.toDatabase(headUsers);

        disciplines.get(0).setHeadOfDiscipline(headUsers.get(0));
        disciplineDao.updateDiscipline(disciplines.get(0));

        disciplines.get(1).setHeadOfDiscipline(headUsers.get(1));
        disciplineDao.updateDiscipline(disciplines.get(1));

        disciplines.get(2).setHeadOfDiscipline(headUsers.get(2));
        disciplineDao.updateDiscipline(disciplines.get(2));

        disciplines.get(3).setHeadOfDiscipline(headUsers.get(3));
        disciplineDao.updateDiscipline(disciplines.get(3));


        divider();

        List<User> userById = userDao.getUserById(1);
        System.out.println(userById);
        divider();

        //Users by Task Status
        List<User> userByTaskStatus = userDao.getUserByTaskStatus(TODO);
        System.out.println(userByTaskStatus);
        divider();

        //Users by discipline
        List<User> usersAM = userDao.getAllUsersByDiscipline(AM);
        System.out.println(usersAM.toString());
       divider();

        //Users by role
        List<User> userByRole = userDao.getUserByRole("Head");
        System.out.println(userByRole);
        divider();


        List<Discipline> disciplinesListLessThan2Members = disciplineDao.getDisciplinesByMembersJoins(2);
        System.out.println(disciplinesListLessThan2Members);

        //soft delete
        userDao.delete(headUsers.get(3));
    }



    private static List<Role> rolesFactory(){
        List<Role> list = new ArrayList<>();
        list.add(new Role("Head"));
        list.add(new Role("Employee"));
        list.add(new Role("Intern"));
        return list;
    }



    private static List<Task> tasksFactory(){
        List<Task> list = new ArrayList<>();
        list.add(new Task("T-201","Create Psql Database", Date.valueOf(LocalDate.of(2020, 10, 25)), Date.valueOf(LocalDate.now()), PROGRESS));
        list.add(new Task("T-202","Create Index .html" ,Date.valueOf(LocalDate.of(2020, 10, 25)), Date.valueOf(LocalDate.now()), TODO));
        list.add(new Task("T-203","Create Js scripts" ,Date.valueOf(LocalDate.of(2020, 10, 25)), Date.valueOf(LocalDate.now()), BLOCKED));
        list.add(new Task("T-204","Add Spring security", Date.valueOf(LocalDate.of(2020, 10, 25)), Date.valueOf(LocalDate.now()), TODO));
        list.add(new Task("T-205","Add /login controller" , Date.valueOf(LocalDate.of(2020, 10, 25)), Date.valueOf(LocalDate.now()), BLOCKED));
        return list;
    }



    private static List<Discipline> disciplinesFactory(){
        List<Discipline> list = new ArrayList<>();
         list.add(new Discipline(ANALYST));
        list.add(new Discipline(AM));
        list.add(new Discipline(DEV));
        list.add(new Discipline(TEST));

        return list;
    }



    private static List<User> usersAMFactory(Discipline disciplineList,
                                           List<Role> rolesList,
                                           List<Task> tasksList)
    {
        List<User> list = new ArrayList<>();

        list.add(new User("Dmitri","Sprinceac","dmitri@gmail.com","dsprinceac",Date.valueOf(LocalDate.of(2020, 10, 25)),
                true, disciplineList));
        list.add(new User("Dan","Velescu","dan@gmail.com:","dvelescu",Date.valueOf(LocalDate.of(2020, 10, 25)),
                true, disciplineList));
        list.add(new User("Sorin","Gorea","sorin@gmail.com:","sgorea",Date.valueOf(LocalDate.now()),
                true, disciplineList));
        list.add(new User("Teimur","Delimuhametov","teimur@gmail.com:","tdelimuhametov",Date.valueOf(LocalDate.now()),
                true, disciplineList));
        list.add(new User("Valeria","Jucov","valeria@gmail.com:","vjucov",Date.valueOf(LocalDate.now()),
                true, disciplineList));
        list.add(new User("Nicolae","Semitar","nicolae@gmail.com:","nsemitar", Date.valueOf(LocalDate.now()),
                true, disciplineList));
        list.add(new User("Vadim","Beshliu","vadim@gmail.com","vbeshliu", Date.valueOf(LocalDate.now()),
                true, rolesList,tasksList,disciplineList));
    return list;
    }



    private static List<User> usersDevFactory(Discipline discipline,
                                             List<Role> rolesList,
                                             List<Task> tasksList)
    {
        List<User> list = new ArrayList<>();

        list.add(new User("Dmitre","Sprinceac","dmitr334is@gmail.com","dsprinceacs",Date.valueOf(LocalDate.of(2020, 10, 25)),
                true, discipline));
        list.add(new User("Dane","Velescu","dans@gmail.com:","dvelescus",Date.valueOf(LocalDate.of(2020, 10, 25)),
                true, discipline));
        list.add(new User("Sorien","Gorea","sorisn@gmail.com:","sgoreas",Date.valueOf(LocalDate.now()),
                true, discipline));
        list.add(new User("Teimure","Delimuhametov","teimusr@gmail.com:","tdelimuhametovs",Date.valueOf(LocalDate.now()),
                true, discipline));
        list.add(new User("Valeriae","Jucov","valersia@gmail.com:","vjucovs",Date.valueOf(LocalDate.now()),
                true, discipline));
        list.add(new User("Nicolaee","Semitar","nicoslae@gmail.com:","nsemitars", Date.valueOf(LocalDate.now()),
                true, discipline));
        list.add(new User("Vadiem","Beshliu","vadsim@gmail.com","vbeshlius", Date.valueOf(LocalDate.now()),
                true, rolesList,tasksList,discipline));
        return list;
    }

    private static List<User> headUsersFactory(List<Discipline> disciplines,
                                       List<Role> roles){
        List<User> list = new ArrayList<>();
        list.add(new User("Test","HeadTest","test@mail.com","testHread",Date.valueOf(LocalDate.now()),
                false,Arrays.asList(roles.get(0)), Collections.EMPTY_LIST, disciplines.get(2)));
        list.add(new User("Dev","HeadDev","dev@mail.com","devHead",Date.valueOf(LocalDate.now()),
                false,roles.stream().filter(it -> it.getRoleName().equals("Head")).collect(Collectors.toList()), Collections.EMPTY_LIST, disciplines.get(2)));
        list.add(new User("JAM","HeadJam","jam@mail.com","anHead",Date.valueOf(LocalDate.now()),
                false,roles.stream().filter(it -> it.getRoleName().equals("Head")).collect(Collectors.toList()), Collections.EMPTY_LIST, disciplines.get(2)));
        list.add(new User("Analyst","ANTest","an@mail.com","amHead",Date.valueOf(LocalDate.now()),
                false,roles.stream().filter(it -> it.getRoleName().equals("Head")).collect(Collectors.toList()), Collections.EMPTY_LIST, disciplines.get(2)));

        return list;

    }
    public static void divider(){
        System.out.println("######################################################################");
    }
}

