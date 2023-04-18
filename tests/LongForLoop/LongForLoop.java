package tests.LongForLoop;

import Models.Person;
import java.util.*;

public class LongForLoop {	
	List<Person> people;
	
	public LongForLoop(List<Person> people) {
		super();
		this.people = people;
	}

	public List<Person> foo(int ageLimit) {
		List<Person> res = new ArrayList<>();
		for(int i = 0; i < people.size()/2; ++i) {
			Person person1 = people.get(i);
			Person person2 = people.get(people.size()-1);
			if(person1.getAge() > ageLimit) {
				res.add(person1);
			}
			if(person2.getAge() > ageLimit) {
				res.add(person2);
			}
			if(person1.getAge() == ageLimit) {
				res.add(person1);
				res.add(person2);
			}
			else {
				ageLimit+= 1;
			}
		}
		return res;
	}
	
}
