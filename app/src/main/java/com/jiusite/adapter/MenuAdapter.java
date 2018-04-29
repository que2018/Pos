package com.jiusite.adapter;

import java.util.HashMap;
import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.jiusite.constant.CONS;
import com.jiusite.customview.MenuItemButton;
import com.jiusite.database.model.Category;
import com.jiusite.database.model.PlaceProduct;
import com.jiusite.database.model.Product;
import com.jiusite.global.MenuPath;
import com.jiusite.global.Glob;
import com.jiusite.session.MenuSession;
import com.jiusite.global.PlaceCounter;
import com.jiusite.session.PlaceSession;
import com.jiusite.helper.Func;
import com.jiusite.main.R;


public class MenuAdapter extends BaseAdapter {
	
	private int menuType;
	private ArrayList<HashMap> menuList;
	
	//init menu
    public MenuAdapter() {
		int categoryId = MenuPath.current();
		
		if(MenuSession.hasSubCategories(categoryId)) {
			this.menuType = CONS.MENU_TYPE_CATEGORY;
			ArrayList<Category> categories = MenuSession.getChildCategories(categoryId);
			menuList = initCategoryList(categories);
		} else {
			this.menuType = CONS.MENU_TYPE_PRODUCT;
			ArrayList<Product> products = MenuSession.getProducts(categoryId);
			menuList = initProductList(products);
		}	
    }
	
	//init with assigned categories
	public MenuAdapter(ArrayList<Category> categories) {
		this.menuType = CONS.MENU_TYPE_CATEGORY;  
		
		menuList = initCategoryList(categories);
    }
	
	//init category list
	private ArrayList<HashMap> initCategoryList(ArrayList<Category> categories) {
		
		Glob.backButton.setBackground(Glob.context.getResources().getDrawable(R.drawable.btn_back_category));
		YoYo.with(Techniques.BounceInUp).duration(400).playOn(Glob.backButton);

		int categoryLen = categories.size();
		
		ArrayList<HashMap> menuList = new ArrayList<HashMap>();
		
		try {	
			int count = 0;
			HashMap<Integer, Category> menuRow = new HashMap<Integer, Category>();
			
			for(int i = 0; i < categoryLen; i++) {
				Category category = categories.get(i);
																	
				if(count == 0) {
					menuRow = new HashMap<Integer, Category>();
					menuRow.put(count, category);
				
					if(i == (categoryLen - 1)) {
						menuList.add(menuRow);
					}
					
					count ++;
					
				} else if(count < 1) {
					menuRow.put(count, category);
					
					if(i == (categoryLen - 1)) {
						menuList.add(menuRow);
					}
					
					count++;
					
				} else {
					menuRow.put(count, category);
					menuList.add(menuRow);
					count = 0;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return menuList;
	}
	
	//init product list
	private ArrayList<HashMap> initProductList(ArrayList<Product> products) {
		YoYo.with(Techniques.BounceInUp).duration(400).playOn(Glob.backButton);
		
		int productLen = products.size();
		
		ArrayList<HashMap> menuList = new ArrayList<HashMap>();
		
		try {	
			int count = 0;
			HashMap<Integer, Product> menuRow = new HashMap<Integer, Product>();
			
			for(int i = 0; i < productLen; i++) {
				Product product = products.get(i);
																	
				if(count == 0) {
					menuRow = new HashMap<Integer, Product>();
					menuRow.put(count, product);
				
					if(i == (productLen - 1)) {
						menuList.add(menuRow);
					}
					
					count ++;
					
				} else if(count < 1) {
					menuRow.put(count, product);
					
					if(i == (productLen - 1)) {
						menuList.add(menuRow);
					}
					
					count++;
					
				} else {
					menuRow.put(count, product);
					menuList.add(menuRow);
					count = 0;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return menuList;
	}
	
    @Override
    public int getCount() {
        return menuList.size();
    } 

    @Override
    public HashMap getItem(int position) {	
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = new View(Glob.context);
		
		if(menuType == CONS.MENU_TYPE_CATEGORY) 
			view = getCategoryView(position, convertView, parent);
		
			
		if(menuType == CONS.MENU_TYPE_PRODUCT)
			view = getProductView(position, convertView, parent);
		
		return view;
    }
	
	private View getCategoryView(int position, View convertView, ViewGroup parent) {
		
		View menuView;
		HashMap<Integer, Category> item = getItem(position);
		
		int size = item.size();	
		
		int R_layout_id;
			
		switch(size) {
			case 1:
				R_layout_id = R.layout.menu_item_row1;
				break;
			default:
				R_layout_id = R.layout.menu_item_row2;
				break;
		}
		
		menuView = LayoutInflater.from(parent.getContext()).inflate(R_layout_id, parent, false);
		
		Category category0 = item.get(0);
			
		MenuItemButton menuItemButton0 = (MenuItemButton)menuView.findViewById(R.id.menu_item0);
		String name0 = category0.getName();
		int textSize0 = Func.measureTextSize(name0);
		menuItemButton0.setTextSize(textSize0);
		menuItemButton0.setText(name0);
		
		menuItemButton0.setOnClickListener(new CategoryListener(category0));
				
		if(size > 1) {
			Category category1 = item.get(1);
			
			MenuItemButton menuItemButton1 = (MenuItemButton)menuView.findViewById(R.id.menu_item1);
			String name1 = category1.getName();
			int textSize1 = Func.measureTextSize(name1);
			menuItemButton1.setTextSize(textSize1);
			menuItemButton1.setText(name1);
			
			menuItemButton1.setOnClickListener(new CategoryListener(category1));
		} 
			
        return menuView;
	}
	
	private View getProductView(int position, View convertView, ViewGroup parent) {
		
		View menuView;
		HashMap<Integer, Product> item = getItem(position);
		
		int size = item.size();	
		
		int R_layout_id;
			
		switch(size) {
			case 1:
				R_layout_id = R.layout.product_item_row1;
				break;
			default:
				R_layout_id = R.layout.product_item_row2;
				break;
		}
		
		menuView = LayoutInflater.from(parent.getContext()).inflate(R_layout_id, parent, false);
		
		Product product0 = item.get(0);
			
		MenuItemButton menuItemButton0 = (MenuItemButton)menuView.findViewById(R.id.product_item0);
		String name0 = product0.getName();
		int textSize0 = Func.measureTextSize(name0);
		menuItemButton0.setTextSize(textSize0);
		menuItemButton0.setText(name0);
		
		menuItemButton0.setOnClickListener(new ProductListener(product0));
				
		if(size > 1) {
			Product product1 = item.get(1);
			
			MenuItemButton menuItemButton1 = (MenuItemButton)menuView.findViewById(R.id.product_item1);
			String name1 = product1.getName();
			int textSize1 = Func.measureTextSize(name1);
			menuItemButton1.setTextSize(textSize1);
			menuItemButton1.setText(name1);
			
			menuItemButton1.setOnClickListener(new ProductListener(product1));
		} 
			
        return menuView;
	}
	
	//category listener
	private class CategoryListener implements View.OnClickListener { 
		
		private Category category;
		
		public CategoryListener(Category category) {
			this.category = category;
		}
	
        @Override
        public void onClick(View view) {
			int categoryId = category.getCategoryId();
			
			//renew category path
			MenuPath.add(categoryId);
			
			ArrayList categories = MenuSession.getChildCategories(categoryId);
		
			//if has sub categories
			if(categories.size() > 0) {
				menuList = initCategoryList(categories);
				notifyDataSetChanged();
			
			//if has no sub categories
			} else {
				menuType = CONS.MENU_TYPE_PRODUCT;
				ArrayList products = MenuSession.getProducts(categoryId);
				menuList = initProductList(products);
				notifyDataSetChanged();
			} 
        }
    }

	//product listener
	private class ProductListener implements View.OnClickListener { 
		
		private Product product;
		
		public ProductListener(Product product) {
			this.product = product;
		}
	
        @Override
        public void onClick(View view) {
			if(PlaceSession.customer != null) {
				int productId = product.getProductId();
				String name = product.getName();
				double price = product.getPrice();
				
				PlaceProduct placeProduct = new PlaceProduct();
				placeProduct.setProductId(productId);
				placeProduct.setName(name);
				placeProduct.setQuantity(1);
				placeProduct.setPrice(price);
				
				PlaceSession.updatePlaceProduct(placeProduct);
				PlaceSession.updated = false;
				
				//display count
				PlaceCounter.setCount(productId);
				
				//update placefragment page
				Glob.placeFragment.updatePlaceView(false);
			
			} else {
				Glob.posViewPager.setCurrentItem(2, true);
			}
        }
    } 	
} 


