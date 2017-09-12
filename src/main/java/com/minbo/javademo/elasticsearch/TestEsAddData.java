package com.minbo.javademo.elasticsearch;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.Random;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.minbo.javademo.utils.UUID;

public class TestEsAddData {

	public static void main(String[] args) throws IOException {
		String ip = "182.92.82.188";

		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		Client client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), 9300));

//		for (int i = 0; i < 100; i++) {
			System.out.println("=============================");
			String name = getRandomEnglishName();
			// 2. 添加索引数据
			XContentBuilder doc = XContentFactory.jsonBuilder()
					.startObject()
						.field("title", "Kevin this Thompson, Kevin is Thompson")
						.field("description", "descript " + name)
						.field("price", new Random().nextInt(100))
						.field("onSale", new Random().nextInt(10) > 5 ? true : false)
						.field("type", new Random().nextInt(10))
						.field("createDate", new Date())
					.endObject();

			IndexResponse response = client.prepareIndex("productindex", "productType", UUID.getUUID()).setSource(doc)
					.execute().actionGet();
			// Index name
			String _index = response.getIndex();
			System.out.println("_index = " + _index);

			// Type name
			String _type = response.getType();
			System.out.println("_type = " + _type);

			// Document ID (generated or not)
			String _id = response.getId();
			System.out.println("_id = " + _id);
			// Version (if it's the first time you index this document, you will get: 1)
			long _version = response.getVersion();
			System.out.println("_version = " + _version);

			// status has stored current instance statement.
			RestStatus status = response.status();
			System.out.println("status = " + status);
			
			System.out.println("Result=" + response.getResult());
//		}

		client.close();
		
	}

	public static final String[] FEMALE_FIRST_NAMES = { "Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "Jennifer",
			"Maria", "Susan", "Margaret", "Dorothy", "Lisa", "Nancy", "Karen", "Betty", "Helen", "Sandra", "Donna",
			"Carol", "Ruth", "Sharon", "Michelle", "Laura", "Sarah", "Kimberly", "Deborah", "Jessica", "Shirley",
			"Cynthia", "Angela", "Melissa", "Brenda", "Amy", "Anna", "Rebecca", "Virginia", "Kathleen", "Pamela",
			"Martha", "Debra", "Amanda", "Stephanie", "Carolyn", "Christine", "Marie", "Janet", "Catherine", "Frances",
			"Ann", "Joyce", "Diane", "Alice", "Julie", "Heather", "Teresa", "Doris", "Gloria", "Evelyn", "Jean",
			"Cheryl", "Mildred", "Katherine", "Joan", "Ashley", "Judith", "Rose", "Janice", "Kelly", "Nicole", "Judy",
			"Christina", "Kathy", "Theresa", "Beverly", "Denise", "Tammy", "Irene", "Jane", "Lori", "Rachel", "Marilyn",
			"Andrea", "Kathryn", "Louise", "Sara", "Anne", "Jacqueline", "Wanda", "Bonnie", "Julia", "Ruby", "Lois",
			"Tina", "Phyllis", "Norma", "Paula", "Diana", "Annie", "Lillian", "Emily", "Robin", "Peggy", "Crystal",
			"Gladys", "Rita", "Dawn", "Connie", "Florence", "Tracy", "Edna", "Tiffany", "Carmen", "Rosa", "Cindy",
			"Grace", "Wendy", "Victoria", "Edith", "Kim", "Sherry", "Sylvia", "Josephine", "Thelma", "Shannon",
			"Sheila", "Ethel", "Ellen", "Elaine", "Marjorie", "Carrie", "Charlotte", "Monica", "Esther", "Pauline",
			"Emma", "Juanita", "Anita", "Rhonda", "Hazel", "Amber", "Eva", "Debbie", "April", "Leslie", "Clara",
			"Lucille", "Jamie", "Joanne", "Eleanor", "Valerie", "Danielle", "Megan", "Alicia", "Suzanne", "Michele",
			"Gail", "Bertha", "Darlene", "Veronica", "Jill", "Erin", "Geraldine", "Lauren", "Cathy", "Joann",
			"Lorraine", "Lynn", "Sally", "Regina", "Erica", "Beatrice", "Dolores", "Bernice", "Audrey", "Yvonne",
			"Annette", "June", "Samantha", "Marion", "Dana", "Stacy", "Ana", "Renee", "Ida", "Vivian", "Roberta",
			"Holly", "Brittany", "Melanie", "Loretta", "Yolanda", "Jeanette", "Laurie", "Katie", "Kristen", "Vanessa",
			"Alma", "Sue", "Elsie", "Beth", "Jeanne" };
	
	private static final String[] MALE_FIRST_NAMES = {"James", "John", "Robert", "Michael", "William", "David", "Richard", "Charles", "Joseph","Thomas", "Christopher", "Daniel", "Paul", "Mark", "Donald", "George", "Kenneth", "Steven","Edward", "Brian", "Ronald", "Anthony", "Kevin", "Jason", "Matthew", "Gary", "Timothy","Jose", "Larry", "Jeffrey", "Frank", "Scott", "Eric", "Stephen", "Andrew", "Raymond","Gregory", "Joshua", "Jerry", "Dennis", "Walter", "Patrick", "Peter", "Harold", "Douglas","Henry", "Carl", "Arthur", "Ryan", "Roger", "Joe", "Juan", "Jack", "Albert", "Jonathan","Justin", "Terry", "Gerald", "Keith", "Samuel", "Willie", "Ralph", "Lawrence", "Nicholas","Roy", "Ben",
			"jamin", "Bruce", "Brandon", "Adam", "Harry", "Fred", "Wayne", "Billy", "Steve","Louis", "Jeremy", "Aaron", "Randy", "Howard", "Eugene", "Carlos", "Russell", "Bobby","Victor", "Martin", "Ernest", "Phillip", "Todd", "Jesse", "Craig", "Alan", "Shawn","Clarence", "Sean", "Philip", "Chris", "Johnny", "Earl", "Jimmy", "Antonio", "Danny","Bryan", "Tony", "Luis", "Mike", "Stanley", "Leonard", "Nathan", "Dale", "Manuel", "Rodney","Curtis", "Norman", "Allen", "Marvin", "Vincent", "Glenn", "Jeffery", "Travis", "Jeff","Chad", "Jacob", "Lee", "Melvin", "Alfred", "Kyle", "Francis", "Bradley", "Jesus", "Herbert","Frederick", "Ray", "Joel", "Edwin", "Don", "Eddie", "Ricky", "Troy", 
			"Randall", "Barry","Alexander", "Bernard", "Mario", "Leroy", "Francisco", "Marcus", "Micheal", "Theodore","Clifford", "Miguel", "Oscar", "Jay", "Jim", "Tom", "Calvin", "Alex", "Jon", "Ronnie","Bill", "Lloyd", "Tommy", "Leon", "Derek", "Warren", "Darrell", "Jerome", "Floyd", "Leo","Alvin", "Tim", "Wesley", "Gordon", "Dean", "Greg", "Jorge", "Dustin", "Pedro", "Derrick","Dan", "Lewis", "Zachary", "Corey", "Herman", "Maurice", "Vernon", "Roberto", "Clyde","Glen", "Hector", "Shane", "Ricardo", "Sam", "Rick", "Lester", "Brent", "Ramon", "Charlie","Tyler", "Gilbert", "Gene"};
	
	public static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore","Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia","Martinez", "Robinson", "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen","Young", "Hernandez", "King", "Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker","Gonzalez", "Nelson", "Carter", "Mitchell", "Perez", "Roberts", "Turner", "Phillips","Campbell", "Parker", "Evans", "Edwards", "Collins", "Stewart", "Sanchez", "Morris","Rogers", "Reed", "Cook", "Morgan", "Bell", "Murphy", "Bailey", "Rivera", "Cooper","Richardson", "Cox", "Howard", "Ward", "Torres", "Peterson", "Gray", "Ramirez", "James","Watson", "Brooks", "Kelly", "Sanders", "Price", "Bennett", "Wood", "Barnes", "Ross","Henderson", "Coleman", "Jenkins", "Perry", "Powell", "Long", "Patterson", "Hughes","Flores", "Washington", "Butler", "Simmons", "Foster", "Gonzales", "Bryant", "Alexander","Russell", "Griffin", "Diaz", "Hayes", "Myers", "Ford", "Hamilton", "Graham", "Sullivan","Wallace", "Woods", "Cole", "West", "Jordan", "Owens", "Reynolds", "Fisher", "Ellis","Harrison", "Gibson", "Mcdonald", "Cruz", "Marshall", "Ortiz", "Gomez", "Murray", "Freeman","Wells", "Webb", "Simpson", "Stevens", "Tucker", "Porter", "Hunter", "Hicks", "Crawford","Henry", "Boyd", "Mason", "Morales", "Kennedy", "Warren", "Dixon", "Ramos", "Reyes", "Burns","Gordon", "Shaw"

			, "Holmes", "Rice", "Robertson", "Hunt", "Black", "Daniels", "Palmer","Mills", "Nichols", "Grant", "Knight", "Ferguson", "Rose", "Stone", "Hawkins", "Dunn","Perkins", "Hudson", "Spencer", "Gardner", "Stephens", "Payne", "Pierce", "Berry","Matthews", "Arnold", "Wagner", "Willis", "Ray", "Watkins", "Olson", "Carroll", "Duncan","Snyder", "Hart", "Cunningham", "Bradley", "Lane", "Andrews", "Ruiz", "Harper", "Fox","Riley", "Armstrong", "Carpenter", "Weaver", "Greene", "Lawrence", "Elliott", "Chavez","Sims", "Austin", "Peters", "Kelley", "Franklin", "Lawson"};
	public final static String[] EMAIL_SUFFIX = { "qq.com", "126.com", "163.com", "gmail.com", "163.net", "msn.com",
			"hotmail.com", "yahoo.com.cn", "sina.com", "@mail.com", "263.net", "sohu.com", "21cn.com", "sogou.com" };

	public final static <T> T nextValue(T[] array) {
		assert (array != null && array.length > 0);
		return array[new Random().nextInt(array.length)];
	}

	public final static String getRandomEnglishName() {
		return getRandomEnglishFirstName() + " " + getRandomEnglishLastName();
	}

	public final static String getRandomEnglishFirstName() {
		return new Random().nextBoolean() ? nextValue(FEMALE_FIRST_NAMES) : nextValue(MALE_FIRST_NAMES);
	}

	public final static String getRandomEnglishLastName() {
		return nextValue(LAST_NAMES);
	}

	public final static String getRandomEmailAddress() {
		return getRandomEnglishFirstName() + getRandomEnglishLastName() + "@" + nextValue(EMAIL_SUFFIX);
	}
}