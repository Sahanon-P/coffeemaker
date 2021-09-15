/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 * 
 * Permission has been explicitly granted to the University of Minnesota 
 * Software Engineering Center to use and distribute this source for 
 * educational purposes, including delivering online education through
 * Coursera or other entities.  
 * 
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including 
 * fitness for purpose.
 * 
 * 
 * Modifications 
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to 
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

import io.cucumber.java.en.*;

/**
 * Unit tests for CoffeeMaker class.
 * 
 * @author Sarah Heckman
 */
public class CoffeeMakerTest {
	
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
	@Before
	public void setUp() throws RecipeException {
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
	
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddInventory() throws InventoryException {
		coffeeMaker.addInventory("4","7","5","9");
	}
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with negative number of coffee
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryNegativeAmountOfCoffee() throws InventoryException {
		coffeeMaker.addInventory("-1", "1", "1", "1");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with negative number of milk
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryNegativeAmountOfMilk() throws InventoryException {
		coffeeMaker.addInventory("1", "-1", "1", "1");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with negative number of sugar
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryNegativeAmountOfSugar() throws InventoryException {
		coffeeMaker.addInventory("1", "1", "-1", "1");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with negative number of chocolate
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryNegativeAmountOfChocolate() throws InventoryException {
		// We need to make the amount of sugar to be negative because it
		// the positive ampunt of sigar will always catch the exception so
		// we need to make them negative in order to reach the chocolate amount
		coffeeMaker.addInventory("1", "1", "-1", "-1");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with non integer quantities of coffee
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryWithNonIntegerOfCoffee() throws InventoryException {
		coffeeMaker.addInventory("asd", "1", "1","1");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with non integer quantities of milk
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryWithNonIntegerOfMilk() throws InventoryException {
		coffeeMaker.addInventory("1", "asd", "1","1");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with non integer quantities of sugar
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryWithNonIntegerOfSugar() throws InventoryException {
		coffeeMaker.addInventory("1", "1", "asd","1");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with non integer quantities of chocolate
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryWithNonIntegerOfChocolate() throws InventoryException {
		// We need to make the amount of sugar to be negative because it
		// the positive ampunt of sigar will always catch the exception so
		// we need to make them negative in order to reach the chocolate amount
		coffeeMaker.addInventory("1", "1", "-1","asd");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with well-formed quantities
	 * Then invoke method checkInventory() to check that the outcome match the expected result.
	 */
	@Test 
	public void testCheckInventory() throws InventoryException {
		coffeeMaker.addInventory("4","7","0","9");
		String temp = "Coffee: 19\nMilk: 22\nSugar: 15\nChocolate: 24\n";
		assertEquals(temp, coffeeMaker.checkInventory());
	}
	
	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying more than 
	 * 		the coffee costs
	 * Then we get the correct change back.
	 */
	@Test
	public void testMakeCoffee() {
		when(mockCoffeeMaker.getRecipes()).thenReturn(recipeList);
		assertEquals(25, mockCoffeeMaker.makeCoffee(0, 75));
	}

	/**
	 * Given a coffee maker with none of recipe.
	 * When we make coffee, selecting the unexisting recipe and pay for 
	 * 		the coffee costs
	 * we will get the paying amount back.
	 */
	@Test
	public void testMakeUnexistingCoffee(){
		when(mockCoffeeMaker.getRecipes()).thenReturn(new Recipe[1]);
		assertEquals(25, mockCoffeeMaker.makeCoffee(0, 25));
	} 

	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying less than 
	 * 		the coffee costs
	 * Then we get the paying amount back.
	 */
	@Test
	public void testMakeCoffeeWithInsufficientAmount() {
		when(mockCoffeeMaker.getRecipes()).thenReturn(recipeList);
		assertEquals(25, mockCoffeeMaker.makeCoffee(1, 25));
	}

	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying the exact  
	 * 		amount of the coffee costs
	 * Then we will not get any change.
	 */
	@Test
	public void testMakeCoffeeWithExactAmount(){
		when(mockCoffeeMaker.getRecipes()).thenReturn(recipeList);
		assertEquals(0, mockCoffeeMaker.makeCoffee(2, 100));
	}

	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee and chocolate amount is not enough 
	 * Then we will get paying amount back.
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Test
	public void testMakeCoffeeWithInsufficientAmountOfChocolate() throws RecipeException{
		when(mockCoffeeMaker.getRecipes()).thenReturn(customRecipeList);
		assertEquals(100, mockCoffeeMaker.makeCoffee(0, 100));

	}

	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee and coffee amount is not enough 
	 * Then we will get paying amount back.
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Test
	public void testMakeCoffeeWithInsufficientAmountOfCoffee() throws RecipeException{
		when(mockCoffeeMaker.getRecipes()).thenReturn(customRecipeList);
		assertEquals(100, mockCoffeeMaker.makeCoffee(1, 100));

	}

	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee and milk amount is not enough 
	 * Then we will get paying amount back.
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Test
	public void testMakeCoffeeWithInsufficientAmountOfMilk() throws RecipeException{
		when(mockCoffeeMaker.getRecipes()).thenReturn(customRecipeList);
		assertEquals(100, mockCoffeeMaker.makeCoffee(2, 100));

	}
	
	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee and sugar amount is not enough 
	 * Then we will get paying amount back.
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Test
	public void testMakeCoffeeWithInsufficientAmountOfSugar() throws RecipeException{
		when(mockCoffeeMaker.getRecipes()).thenReturn(customRecipeList);
		assertEquals(100, mockCoffeeMaker.makeCoffee(3, 100));

	}

	/**
	 * Given a coffee maker with three valid recipes
	 * When we make add three recipes it should work without any error. 
	 * 
	 */
	@Test 
	public void testAddThreeRecipe(){
		assertTrue(coffeeMaker.addRecipe(recipe1));
		assertTrue(coffeeMaker.addRecipe(recipe2));
		assertTrue(coffeeMaker.addRecipe(recipe3));
	}

	/**
	 * Given a coffee maker with four valid recipes
	 * When we add four recipes it should throws the RecipeException. 
	 * 	because only three recipe can be added to recipe book.
	 */
	@Test(expected = RecipeException.class)
	public void testAddExceedAmountOfRecipe(){
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		coffeeMaker.addRecipe(recipe4);
	}

	/**
	 * Given a coffee maker with one valid recipe 
	 * When we try to add same recipe twice it should return false in the second time.
	 */
	@Test 
	public void testAddExistingRecipe(){
		coffeeMaker.addRecipe(recipe1);
		assertFalse(coffeeMaker.addRecipe(recipe1));
	}


	/**
	 * Given a coffee maker with one valid recipe.
	 * When we delete a recipe it should return a deleted coffee name.
	 */
	@Test
	public void testDeleteExistingRecipe(){
		coffeeMaker.addRecipe(recipe1);
		assertEquals("Coffee", coffeeMaker.deleteRecipe(0));
	}

	/**
	 * Given a coffee maker with none of recipe
	 * When we try to delete an unexisting recipe it should return null.
	 */
	@Test 
	public void testDeleteUnexistingRecipe(){
		assertEquals(null, coffeeMaker.deleteRecipe(0));
	}

	/**
	 * Given a coffee maker with one valid recipe 
	 * When we edit the recipe it should return the name of the edited recipe 
	 * 	the edited recipe name should not be changed.
	 */
	@Test
	public void testEditRecipe(){
		coffeeMaker.addRecipe(recipe1);
		assertEquals("Coffee", coffeeMaker.editRecipe(0, recipe2));
		assertEquals("Coffee" , coffeeMaker.getRecipes()[0].getName());
	}

	/**
	 * Given a coffee maker with none of recipe
	 * When we try to edit an unexisting recipe it should return null.
	 */
	@Test 
	public void testEditUnexistingRecipe(){
		assertEquals(null, coffeeMaker.editRecipe(0, recipe2));
	}

	/**
	 * Given a coffee maker with one valid recipe
	 * When we purchase a coffee, the inventory ingredient should be decrease by the
	 * 	amount of coffee recipe. 
	 */
	@Test
	public void testInventoryAfterPurchased(){
		when(mockCoffeeMaker.getRecipes()).thenReturn(recipeList);
		coffeeMaker.makeCoffee(2, 100);
		String temp = "Coffee: 12\nMilk: 12\nSugar: 14\nChocolate: 15\n";
		assertEquals(temp, coffeeMaker.checkInventory());

	}
}