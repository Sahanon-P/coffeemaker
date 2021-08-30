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

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

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
	
	// Sample recipes to use in testing.
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker} 
	 * object we wish to test.
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		coffeeMaker = new CoffeeMaker();
		
		//Set up for r1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");
		
		//Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");
		
		//Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");
		
		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
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
	 * When we add inventory with malformed quantities (i.e., a negative 
	 * quantity and a non-numeric string)
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryException() throws InventoryException {
		coffeeMaker.addInventory("4", "-1", "asdf", "3");
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
		coffeeMaker.addRecipe(recipe1);
		assertEquals(25, coffeeMaker.makeCoffee(0, 75));
	}

	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying less than 
	 * 		the coffee costs
	 * Then we get the paying amount back.
	 */
	@Test
	public void testMakeCoffeeWithInsufficientAmount() {
		coffeeMaker.addRecipe(recipe2);
		assertEquals(25, coffeeMaker.makeCoffee(0, 25));
	}

	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying the exact  
	 * 		amount of the coffee costs
	 * Then we will not get any change.
	 */
	@Test
	public void testMakeCoffeeWithExactAmount(){
		coffeeMaker.addRecipe(recipe3);
		assertEquals(0, coffeeMaker.makeCoffee(0, 100));
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
		coffeeMaker.addRecipe(recipe3);
		coffeeMaker.makeCoffee(0, 100);
		String temp = "Coffee: 12\nMilk: 12\nSugar: 14\nChocolate: 15\n";
		assertEquals(temp, coffeeMaker.checkInventory());

	}
}