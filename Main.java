import java.util.*;
import java.io.*;

/** GET TOTAL ASSETS:
constructionEquipment +
getTotalCurrentAssets() +
autos +
officeEquipment -
accumulatedDepreciation + 
land

GET TOTAL CURRENT ASSETS:
cashOnHand +
currentAccountsReceivable +
retentionReceivable +
unbilledAmount + 
underbilledAmount

GET TOTAL LIABILITIES AND EQUITIES:
getTotalCurrentLiabilites() +
equity

GET TOTAL CURRENT LIABILITIES:
currentAccountsPayable +
retentionPayable +
overbilledAmount +
taxes +
loanBalances

**/

public class Main
{
	static Random rand = new Random();
	public static void main(String[] args)
	{
		buildData();
		readInData("rawDataAssets.txt", "rawDataL+E.txt");
	}

	public static void buildData()
	{
		String[] assetCats = {"Construction Equipment: $", "Autos: $", "Office Equipment: $", "Accumulated Depreciation: $",
						 "Land: $", "Cash On Hand: $", "Current Accounts Receivable: $", "Retention Receivable: $",
						 "Unbilled Amount: $", "Underbilled Amount: $"};
		String[] l_plus_e_Cats = {"Current Accounts Payable: $", "Retention Payable: $", "Overbilled Amount: $", "Taxes: $",
								  "Loan Balances: $"};
		long equity = 0, count = 0;
		try (PrintWriter writer = new PrintWriter(new File("rawDataAssets.txt")))
		{
			try (PrintWriter writer2 = new PrintWriter(new File("rawDataL+E.txt")))
			{
				StringBuilder builder1 = new StringBuilder();
				StringBuilder builder2 = new StringBuilder();
				for (int i = 0; i < 100; i++)
				{
					//builder1.append("-----#" + ++count + "------\n");
					//builder2.append("-----#" + count + "------\n");
					for (int j = 0; j < 10; j++)
					{
						String cat = "";

						/** EDIT VALUE FOR randNum FOR DIFFERENT RESULTS
						.i.e (it seems to work when randNum is 10, 100, 5000, 50,000,000, etc...)
						randNum is a randomly generated value from range 0 -> the number input - 1
						so, randNum = rand.nextInt(71) is a random number from 0 -> 70

						These random numbers are being used as fill-in data for things like office equipment, taxes, etc...

						seems to work with numbers: <500000000 (eight zeros)
					    multiple breaks with numbers: >700000000 (eight zeros)

						When the random number range is too large, something causes these very large numbers to break in addition,
						possibly overflow or some other issue.

					    **/

						long randNum = rand.nextInt(700000000);
						builder1.append(assetCats[j] + randNum + "\n");
						if (j == 5)
						{
							builder2.append("Equity: $" + equity + "\n");
							equity = 0;
						}
						if (j > 4)
						{
							builder2.append(l_plus_e_Cats[j - 5] + randNum  + "\n");
						}
						if (j <= 4)
						{
							equity = equity + randNum;
						}
					}
				}
				writer.write(builder1.toString());
				writer.close();
				writer2.write(builder2.toString());
				writer2.close();
			} catch (FileNotFoundException e)
			{
				System.out.println("Error creating L+E file. Please ensure file is closed.");
			}
		} catch (FileNotFoundException e)
		{
			System.out.print("Error creating assets file. Please ensure file is closed.");
			System.exit(1);
		}
	}

	public static void readInData(String fileAssets, String fileLandE)
	{
		long constructionEquipment = 0, autos = 0, officeEquipment = 0, accumulatedDepreciation = 0, land = 0, cashOnHand = 0,
			currentAccountsReceivable = 0, retentionReceivable = 0, unbilledAmount = 0, underbilledAmount = 0;
		long equity = 0, currentAccountsPayable = 0, retentionPayable = 0, overbilledAmount = 0, taxes = 0, loanBalances = 0;
		long totalAssets = 0, currentAssets = 0, currentLiabilities = 0, totalLiabilitiesAndEquity = 0;

		int assetCount = 0, eqCount = 0;

		try
		{
			Scanner readerAssets = new Scanner(new File(fileAssets));
			Scanner readerLplusE = new Scanner(new File(fileLandE));
			PrintWriter writer = new PrintWriter(new File("results.txt"));
			StringBuilder builder = new StringBuilder();

			while (readerAssets.hasNext())
			{
				String line = readerAssets.nextLine();
				line = line.substring(line.lastIndexOf("$") + 1);
				long longLine = Long.parseLong(line);
				//int intLine = Integer.parseInt(line);
				switch (assetCount)
				{
					case 0:
						//constructionEquipment = intLine;
						constructionEquipment = longLine;
						assetCount++;
						break;
					case 1:
						//autos = intLine;
						autos = longLine;
						assetCount++;
						break;
					case 2:
						//officeEquipment = intLine;
						officeEquipment = longLine;
						assetCount++;
						break;
					case 3:
						//accumulatedDepreciation = intLine;
						accumulatedDepreciation = longLine;
						assetCount++;
						break;
					case 4:
						//land = intLine;
						land = longLine;
						assetCount++;
						break;
					case 5:
						//cashOnHand = intLine;
						cashOnHand = longLine;
						assetCount++;
						break;
					case 6:
						//currentAccountsReceivable = intLine;
						currentAccountsReceivable = longLine;
						assetCount++;
						break;
					case 7:
						//retentionReceivable = intLine;
						retentionReceivable = longLine;
						assetCount++;
						break;
					case 8:
						//unbilledAmount = intLine;
						unbilledAmount = longLine;
						assetCount++;
						break;
					case 9:
						//underbilledAmount = intLine;
						underbilledAmount = longLine;
						currentAssets = cashOnHand + currentAccountsReceivable + retentionReceivable +
												unbilledAmount + underbilledAmount;
						totalAssets = currentAssets + constructionEquipment + autos + officeEquipment +
									  accumulatedDepreciation + land;
						break;
					default:
					//do nothing, should never reach here really
						break;
				}
				if (assetCount > 4)
				{
					String line2 = readerLplusE.nextLine();
					line2 = line2.substring(line2.lastIndexOf("$") + 1);
					long longLine2 = Long.parseLong(line2);
					switch (eqCount)
					{
						case 0:
							//equity = Integer.parseInt(line2);
							equity = longLine2;
							eqCount++;
							break;
						case 1:
							//currentAccountsPayable = Integer.parseInt(line2);
							currentAccountsPayable = longLine2;
							eqCount++;
							break;
						case 2:
							//retentionPayable = Integer.parseInt(line2);
							retentionPayable = longLine2;
							eqCount++;
							break;
						case 3:
							//overbilledAmount = Integer.parseInt(line2);
							overbilledAmount = longLine2;
							eqCount++;
							break;
						case 4:
							//taxes = Integer.parseInt(line2);
							taxes = longLine2;
							eqCount++;
							break;
						case 5:
							//loanBalances = Integer.parseInt(line2);
							loanBalances = longLine2;
							eqCount = 0;
							currentLiabilities = currentAccountsPayable + retentionPayable + overbilledAmount + taxes + loanBalances;
							totalLiabilitiesAndEquity = currentLiabilities + equity;
							builder.append("Assets: " + totalAssets + "\nL+E: " + totalLiabilitiesAndEquity + "\n");
							builder.append(assertBalance(totalAssets, totalLiabilitiesAndEquity) + "\n");
							writer.write(builder.toString());

							currentLiabilities = 0;
							totalLiabilitiesAndEquity = 0;
							totalAssets = 0;
							currentAssets = 0;
							assetCount = 0;
							break;
						default:
						//do nothing, should never reach here really
							break;
					}
				}
			}
		} catch (FileNotFoundException e)
		{
			System.out.println("Invalid input file error. Please ensure file is closed.");
			System.exit(1);
		}
	}

	public static boolean assertBalance(long assets, long liabilities)
	{
		boolean balance = false;
	  	long l = assets - liabilities;
	  	if ((l >= -5) && (l <= 5))
	  	{
		  	balance = true;
	  	}

	  	return balance;
	}
}