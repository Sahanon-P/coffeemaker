package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

import io.cucumber.java.en.*;


public class CoffeeMakerFeatureTest {
    /**
	 * The object under test.
	 */
	private CoffeeMaker coffeeMaker;
	private CoffeeMaker mockCoffeeMaker;
	private Inventory inventory;
	private RecipeBook recipeBook;

	// Sample recipes to use in testing.
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;
	private Recipe recipe5;
	private Recipe recipe6;
	private Recipe recipe7;
	private Recipe recipe8;
	private Recipe[] recipeList;
	private Recipe[] customRecipeList;

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker} 
	 * object we wish to test.
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Given("The coffee maker is ready to serve")
	public void theCoffeeMakerIsReadyToServe() throws RecipeException {
		inventory = new Inventory();
		recipeBook = mock(RecipeBook.class);
		coffeeMaker = new CoffeeMaker();
		mockCoffeeMaker = new CoffeeMaker(recipeBook, inventory);
		//Set up for r1
		recipe1 = new Recipe();
		makeRecipe(recipe1,"Coffee","0","3","1","1","50");
		
		//Set up for r2
		recipe2 = new Recipe();
		makeRecipe(recipe2,"Mocha","20","3","1","1","70");
		
		//Set up for r3
		recipe3 = new Recipe();
		makeRecipe(recipe3,"Latte","0","3","3","1","100");
		
		//Set up for r4
		recipe4 = new Recipe();
		makeRecipe(recipe4,"Hot Chocolate","4","0","1","1","65");

		//Set up for r5 
		recipe5 = new Recipe();
		makeRecipe(recipe5,"Super Chocolate","100","0","0","0","100");

		//Set up for r6 
		recipe6 = new Recipe();
		makeRecipe(recipe6,"Super Coffee","0","100","0","0","100");

		//Set up for r7
		recipe7 = new Recipe();
		makeRecipe(recipe7,"Super Milk","0","0","100","0","100");

		//Set up for r8
		recipe8 = new Recipe();
		makeRecipe(recipe8,"Super Milk","0","0","0","100","100");

		//stubs for original recipe.
		recipeList = new Recipe[] {recipe1, recipe2, recipe3, recipe4};
		//stubs for custom recipe.
		customRecipeList = new Recipe[] {recipe5, recipe6, recipe7, recipe8};

	}

	/**
	 * Set the parameters for the recipe. All the parameter except
	 * 	recipe will be String. 
	 * @param recipe The recipe
	 * @param name The name of the recipe
	 * @param chocolate The amount of chocolate.
	 * @param coffee The amount of coffee.
	 * @param milk The amount of milk.
	 * @param sugar The amount of sugar.
	 * @param price The price of the recipe.
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	private void makeRecipe(Recipe recipe, String name, String chocolate, 
							String coffee, String milk, String sugar, 
							String price) throws RecipeException{
		recipe.setName(name);
		recipe.setAmtChocolate(chocolate);
		recipe.setAmtCoffee(coffee);
		recipe.setAmtMilk(milk);
		recipe.setAmtSugar(sugar);
		recipe.setPrice(price);
	}

    @When("The customer made an order")
    public void theCustomerMadeAnOrder() {
        when(mockCoffeeMaker.getRecipes()).thenReturn(recipeList);
    }

    @Then("The customer choose recipe number {int} and purchase with {int} so the change will be {int}")
    public void theCustomerChooseRecipeNumberAndPurchaseWithSoTheChangeWillBe(Integer number, Integer amount, Integer change) {
        assertEquals(change.intValue(), mockCoffeeMaker.makeCoffee(number, amount));
    }

	@When("The customer made an unexisting order")
    public void theCustomerMadeAnUnexistingOrder() {
        when(mockCoffeeMaker.getRecipes()).thenReturn(new Recipe[1]);
    }

    
}
