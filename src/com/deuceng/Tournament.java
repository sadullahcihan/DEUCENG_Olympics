package com.deuceng;
import java.time.Clock;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

public class Tournament {
	private static FileReader fr;
	private static BufferedReader br;
	private Athlete[] athletes;
	private int athleteNumber;
	private int branchCount;
	private Match[] fixture;
	private Athlete[][] groupedByBranch;

	private Athlete[] athletesSorted;

	private Athlete[][] groupedByCountry;

	public Tournament(String path) throws Exception {
		this.getData(path);
		// this.countUniqueBranches();
		// this.groupeByBranch();
		this.groupedByBranch = this.filterByAttribute(1);
		this.groupedByCountry = this.filterByAttribute(0);
		athletesSorted = sort(athletes, 2);
		this.generateFixture();
		this.performTournaments();

	}

	public static FileReader getFr() {
		return fr;
	}

	public static void setFr(FileReader fr) {
		Tournament.fr = fr;
	}

	public static BufferedReader getBr() {
		return br;
	}

	public static void setBr(BufferedReader br) {
		Tournament.br = br;
	}

	public int getAthleteNumber() {
		return athleteNumber;
	}

	public void setAthleteNumber(int athleteNumber) {
		this.athleteNumber = athleteNumber;
	}

	public Match[] getFixture() {
		return fixture;
	}

	public void setFixture(Match[] fixture) {
		this.fixture = fixture;
	}

	public Athlete[][] getGroupedByBranch() {
		return groupedByBranch;
	}

	public void setGroupedByBranch(Athlete[][] groupedByBranch) {
		this.groupedByBranch = groupedByBranch;
	}

	public Athlete[][] getGroupedByCountry() {
		return groupedByCountry;
	}

	public void setGroupedByCountry(Athlete[][] groupedByCountry) {
		this.groupedByCountry = groupedByCountry;
	}

	public void setAthletes(Athlete[] athletes) {
		this.athletes = athletes;
	}

	public void setBranchCount(int branchCount) {
		this.branchCount = branchCount;
	}

	public int getBranchCount() {
		return branchCount;
	}

	public Athlete[] getAthletes() {
		return athletes;
	}

	public void getData(String path) throws Exception {
		fr = new FileReader(path);
		br = new BufferedReader(fr);

		// get the line length
		while (br.readLine() != null) {
			athleteNumber++;
		}

		fr.close();

		// System.out.println(athleteNumber);
		athletes = new Athlete[athleteNumber];
		// read the file line by line
		fr = new FileReader(path);
		br = new BufferedReader(fr); // re open the file to read from the beginning.
		String s;
		String[] properties;
		String[] splittedDate;
		boolean isValid = true;
		for (int i = 0; i < athletes.length; i++) {
			s = br.readLine();
			properties = s.split(",");
			for (String p : properties) {
				if (p.equals("") || p == null) {
					System.out.println("Input is not valid.");
					isValid = false;
					break;
				} // else {
					// System.out.print(" #" + p + "# ");
					// }

			}
			if (!isValid) {
				break;
			}
			// System.out.println();
			splittedDate = properties[4].split("\\."); // we used two backslashes because dot means split by any
			// letter you see.
			athletes[i] = new Athlete(properties[0], properties[1], properties[2], properties[3],
					new Date(Integer.parseInt(splittedDate[0]), Integer.parseInt(splittedDate[1]),
							Integer.parseInt(splittedDate[2])),
					Float.parseFloat(properties[5]));

		}
	}

	public Athlete[] sort(Athlete[] aths, int attr) {
		int n = aths.length;
		for (int i = 0; i < n - 1; i++)
			for (int j = 0; j < n - i - 1; j++)
				if (Integer.parseInt(aths[j].getAttribute(attr)) < Integer.parseInt(aths[j + 1].getAttribute(attr))) {
					// swap temp and arr[i]
					Athlete temp = aths[j];
					aths[j] = aths[j + 1];
					aths[j + 1] = temp;
				}
		return aths;
	}

	private void generateFixture() {
		fixture = new Match[1000000];
		int m = 0;
		for (int i = 0; i < groupedByBranch.length; i++) {
			for (int j = 0; j < groupedByBranch[i].length; j++) {
				for (int k = j; k < groupedByBranch[i].length; k++) {
					if (groupedByBranch[i][j] != groupedByBranch[i][k] && groupedByBranch[i][j] != null
							&& groupedByBranch[i][k] != null) {
						fixture[m] = new Match(groupedByBranch[i][j], groupedByBranch[i][k]);
						m++;
					}
				}
			}
			// randomize the fixture
			for (int n = 0; n < fixture.length; n++) {
				Random rand = new Random();
				int randIdx = rand.nextInt(fixture.length);

				Match temp = fixture[n];
				fixture[n] = fixture[randIdx];
				fixture[randIdx] = temp;
			}

			// schedule the matches
			int hour = 11;
			int day = 24;
			int month = 7;
			int year = 2020;
			for (int l = 0; l < fixture.length; l++) {
				if (fixture[l] != null) {
					fixture[l].setDate(new Date(hour, day, month, year));
					hour += 2;
					if (hour == 17) {
						hour = 11;
						day++;
					}
					if (day > 30) {
						day = 1;
						month += 1;
					}
					if (month > 12) {
						month = 1;
						year += 1;
					}
				}
			}
		}
	}

	private void performTournaments() {
		for (Match m : fixture) {
			if (m != null) {
				m.compete();
			}
		}
	}

	public void showTournaments() {
		Scanner sc = new Scanner(System.in);
		String option;
		while (true) {
			System.out.println("***MENU***" + "\n" + "1.Generate fixture" + "\n" + "2.Perform tournaments" + "\n"
					+ "3.Show statistics" + "\n" + "4.Exit"); // MENU
			System.out.println("Choose an option above: ");
			option = sc.nextLine();
			if (option.equals("1")) {
				generateFixture();
				for (Match m : fixture) {
					if (m != null)
						System.out.println(m.getAthlete1().getBranch() + " " + m.getAthlete1().getCountry() + "-"
								+ m.getAthlete1().getFullName() + " vs " + m.getAthlete2().getCountry() + "-"
								+ m.getAthlete2().getFullName() + "  Competition Time: " + m.display());

				}
			} else if (option.equals("2")) {
				performTournaments();
				int x = 0;
				for (int i = 0; i < fixture.length; i++) {
					Match m = fixture[i];
					if (m != null) {
						x++;
						System.out.println((x) + "# " + m.getAthlete1().getBranch() + " " + m.getAthlete1().getCountry()
								+ "-" + m.getAthlete1().getFullName() + " [" + m.getAthlete1().getSkill() + "]" + " vs "
								+ m.getAthlete2().getCountry() + "-" + m.getAthlete2().getFullName() + " ["
								+ m.getAthlete2().getSkill() + "]" + " and the winner is: "
								+ m.getWinner().getFullName() + " from " + m.getWinner().getCountry() + " at:  "
								+ m.display());
					}
				}
			} else if (option.equals("3")) {
				System.out.println("***** Statistics Menu *****\n" + "1.The Country which won most of the matches\n"
						+ "2.The Athlete who won most of the matches\n" + "3.List of matches by Date\n"
						+ "4.List of matches by Country");
				String option2 = sc.nextLine();
				if (option2.equals("1")) {// The best country
					System.out.println("The Best Country is " + countryMostWon());
				} else if (option2.equals("2")) {// The best athlete
					Athlete[] athletesSorted = sort(athletes, 2);
					System.out.println("The athlete who won most of the matches: ");
					System.out.println(athletesSorted[0].getFullName() + " Score: " + athletesSorted[0].getPoint());
				} else if (option2.equals("3")) { // list of matches by date
					try {
						System.out.println("Enter Date: ");
						String inputDate = sc.nextLine();
						String[] matchDate = new String[3];
						for (int i = 0; i < matchDate.length; i++) {
							matchDate = inputDate.split("\\.");
						}
						int matchDay = Integer.parseInt(matchDate[0]);
						int matchMonth = Integer.parseInt(matchDate[1]);
						for (int i = 0; i < fixture.length; i++) {
							if (fixture[i] != null) {
								Date d = fixture[i].getDate();
								if (d.getDay() == matchDay && d.getMonth() == matchMonth) {
									System.out.println((d.getDay() + "." + d.getMonth() + "." + d.getYear() + " "
											+ d.getHour() + ":00 " + fixture[i].getAthlete1().getBranch() + " "
											+ fixture[i].getAthlete1().getCountry() + " "
											+ fixture[i].getAthlete1().getFullName() + " VS. " + " "
											+ fixture[i].getAthlete2().getCountry() + " "
											+ fixture[i].getAthlete2().getFullName()));
								}
							}
						}
					} catch (Exception ie) {
						System.out.println("Error! Please enter right input.");
					}
				} else if (option2.equals("4")) {// list of matches by country
					System.out.println("Enter Country:");
					String inputCountry = sc.nextLine();
					inputCountry = inputCountry.toUpperCase();
					for (int i = 0; i < fixture.length; i++) {
						if (fixture[i] != null) {
							if (fixture[i].getAthlete1().getCountry().equals(inputCountry)
									|| fixture[i].getAthlete2().getCountry().equals(inputCountry)) {
								System.out.println((fixture[i].getDate().getDay() + "."
										+ fixture[i].getDate().getMonth() + "." + fixture[i].getDate().getYear() + " "
										+ fixture[i].getDate().getHour() + ":00 " + fixture[i].getAthlete1().getBranch()
										+ " " + fixture[i].getAthlete1().getCountry() + " "
										+ fixture[i].getAthlete1().getFullName() + " VS. " + " "
										+ fixture[i].getAthlete2().getCountry() + " "
										+ fixture[i].getAthlete2().getFullName()));
							}
						}
					}
				} else {
					System.out.println("Error! Please select 1,2,3 or 4");
				}

			} else if (option.equals("4")) {
				break;

			} else {
				System.out.print("Error! Please just choose 1, 2 or 3 ..." + "\n -> ");
			}
		}
	}

	private Athlete[][] filterByAttribute(int attr) {
		for (int i = 0; i < athletes.length; i++) {
			boolean isDistinct = true;
			for (int j = 0; j < i; j++) {
				if (athletes[i] != null && athletes[j] != null
						&& athletes[i].getAttribute(attr).equals(athletes[j].getAttribute(attr))) {
					isDistinct = false;
					break;
				}
			}
			if (isDistinct) {
				branchCount++;
			}
		}
		Athlete[][] objArray;
		objArray = new Athlete[branchCount][20];
		int m = 0;
		for (int i = 0; i < athletes.length; i++) {
			int n = 0;
			boolean isDistinct = true;
			for (int j = 0; j < i; j++) {
				if (athletes[i] != null && athletes[j] != null
						&& athletes[i].getAttribute(attr).equals(athletes[j].getAttribute(attr))) {
					isDistinct = false;
					break;
				}
			}
			if (isDistinct) {
				for (int k = 0; k < athletes.length; k++) {
					if (athletes[i] != null && athletes[k] != null
							&& athletes[i].getAttribute(attr).equals(athletes[k].getAttribute(attr))) { // grouping
																										// athletes by
																										// their branch
						objArray[m][n] = athletes[k]; // m is the branch dimension and n is the athletes
						n++;
					}
				}
				m++;
			}

		}
		return objArray;
	}

	private String countryMostWon() {
		String[][] countries = new String[groupedByCountry.length][2];
		int count;
		int winner = 0;
		int indexWinner = 0;
		for (int i = 0; i < groupedByCountry.length; i++) {
			count = 0;
			for (int j = 0; j < groupedByCountry[i].length; j++) {
				if (groupedByCountry[i][j] != null) {
					countries[i][0] = groupedByCountry[i][j].getCountry();
					count += groupedByCountry[i][j].getPoint();
				}
			}
			countries[i][1] = String.valueOf(count);
		}
		for (int i = 0; i < countries.length; i++) {
			if (Integer.parseInt(countries[i][1]) > winner) {
				indexWinner = i;
				winner = Integer.parseInt(countries[i][1]);
			}
		}
		return countries[indexWinner][0] + " Score:" + winner;
	}

	private String athleteMostWon() {
		Athlete winner = athletesSorted[0];
		for (int i = 0; i < athletesSorted.length; i++) {
			if (winner.getPoint() < athletesSorted[i].getPoint()) {
				winner = athletesSorted[i];
			}
		}
		return winner.getFullName() + " Score:" + winner.getPoint();
	}
}
