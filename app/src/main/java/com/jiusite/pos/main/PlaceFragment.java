package com.jiusite.pos.main;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.jiusite.constant.ADDR;
import com.jiusite.constant.CONS;
import com.jiusite.constant.STATS;
import com.jiusite.adapter.PlaceProductAdapter;
import com.jiusite.customview.SpecialPanel;
import com.jiusite.pos.database.model.OrderProduct;
import com.jiusite.pos.database.model.PlaceProduct;
import com.jiusite.global.Glob;
import com.jiusite.listener.SwipeListener;
import com.jiusite.notification.AlertDialog;
import com.jiusite.notification.SuccessAlertDialog;
import com.jiusite.script.CancelScript;
import com.jiusite.script.PlaceScript;
import com.jiusite.script.UpdateScript;
import com.jiusite.session.OrderSession;
import com.jiusite.global.PlaceCounter;
import com.jiusite.session.PlaceSession;
import com.jiusite.network.PostNetData;
import com.jiusite.notification.PosDialog;


public class PlaceFragment extends PosFragment {

	private Button hideButton;
	private Button viodButton;
	private Button holdButton;
	private Button unsetButton;
	private Button placeButton;
	private Button cancelButton;
	private TextView placeNotice;
	private TextView customerText;
	private SwipeLayout swipeLayout;
	private LinearLayout buttonGroup;
	private ProgressBar progressbar;
	private ListView placeProductList;
	private RelativeLayout placeTable;
	private SpecialPanel specialPanel;
	private ProgressBar progressbarPanel;
	private RecyclerView specialRequestActionList;
	private RecyclerView specialRequestCategoryList;
	private PlaceProductAdapter placeProductAdapter;

	private RelativeLayout nullSection;
	private LinearLayout orderedSection;
	private RelativeLayout placedSection;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//init global vars
		Glob.placeFragment = PlaceFragment.this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//get view
		ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_place, container, false);

		customerText = (TextView)rootView.findViewById(R.id.customer);
		placeNotice = (TextView)rootView.findViewById(R.id.place_notice);

		hideButton = (Button)rootView.findViewById(R.id.hide);
		viodButton = (Button)rootView.findViewById(R.id.viod);
		holdButton = (Button)rootView.findViewById(R.id.hold);
		unsetButton = (Button)rootView.findViewById(R.id.unset);
		placeButton = (Button)rootView.findViewById(R.id.place);
		cancelButton = (Button)rootView.findViewById(R.id.cancel);

		swipeLayout =(SwipeLayout)rootView.findViewById(R.id.swipe);
		progressbar = (ProgressBar)rootView.findViewById(R.id.progressbar);
		placeTable = (RelativeLayout)rootView.findViewById(R.id.place_table);
		specialPanel = (SpecialPanel)rootView.findViewById(R.id.special_panel);
		buttonGroup = (LinearLayout)rootView.findViewById(R.id.button_group);
		progressbarPanel = (ProgressBar)rootView.findViewById(R.id.progressbar_panel);
		placeProductList = (ListView)rootView.findViewById(R.id.place_product_list);

		nullSection = (RelativeLayout)rootView.findViewById(R.id.null_section);
		placedSection = (RelativeLayout)rootView.findViewById(R.id.placed_section);
		orderedSection = (LinearLayout)rootView.findViewById(R.id.ordered_section);

		//disable swipelayout
		swipeLayout.setSwipeEnabled(false);

		specialPanel.bringToFront();
		Glob.specialPanel = specialPanel;

		initButtons();

		updatePlaceView(true);

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void initButtons() {
		placeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				progressbar.setVisibility(View.VISIBLE);
				placeTable.setVisibility(View.INVISIBLE);
				customerText.setVisibility(View.INVISIBLE);

				if(PlaceSession.orderId == 0) {
					PlaceTask placeTask = new PlaceTask();
					placeTask.execute();
				} else {
					UpdateTask updateTask = new UpdateTask();
					updateTask.execute();
				}
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				buttonGroup.setVisibility(View.INVISIBLE);
				progressbarPanel.setVisibility(View.VISIBLE);

				CancelTask cancelTask = new CancelTask();
				cancelTask.execute();
			}
		});

		unsetButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				buttonGroup.setVisibility(View.INVISIBLE);
				progressbarPanel.setVisibility(View.VISIBLE);

				UnsetTask unsetTask = new UnsetTask();
				unsetTask.execute();
			}
		});

		viodButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				buttonGroup.setVisibility(View.INVISIBLE);
				progressbarPanel.setVisibility(View.VISIBLE);

				VoidTask voidTask = new VoidTask();
				voidTask.execute();
			}
		});

		holdButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				buttonGroup.setVisibility(View.INVISIBLE);
				progressbarPanel.setVisibility(View.VISIBLE);

				HoldTask holdTask = new HoldTask();
				holdTask.execute();
			}
		});

		hideButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				specialPanel.setVisibility(View.INVISIBLE);
				Glob.posViewPager.setPagingEnabled(true);
			}
		});

		customerText.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				Vibrator vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
				vibrator.vibrate(150);

				swipeLayout.setSwipeEnabled(true);
				swipeLayout.open(true, false);

				return true;
			}
		});

		swipeLayout.addSwipeListener(new SwipeListener() {
			public void onClose(SwipeLayout layout) {
				layout.setSwipeEnabled(false);
			}
		});
	}

	public void updatePlaceView(boolean force) {
		if(force || !PlaceSession.updated) {
			//seat has placed
			if(PlaceSession.type == CONS.PLACESESSION_PLACED_DINING) {
				String place = getResources().getString(R.string.place);
				String NOtable = getResources().getString(R.string.NO_table);

				placeButton.setText(place);
				customerText.setText(PlaceSession.customer + NOtable);
				customerText.setVisibility(View.VISIBLE);

				nullSection.setVisibility(View.GONE);
				orderedSection.setVisibility(View.GONE);
				placedSection.setVisibility(View.VISIBLE);

				if(!PlaceSession.emptyPlaceProducts()) {
					placeNotice.setVisibility(View.INVISIBLE);
					placeProductAdapter = new PlaceProductAdapter(swipeLayout);
					placeProductList.setAdapter(placeProductAdapter);
				} else {
					placeNotice.setVisibility(View.VISIBLE);
					placeProductList.setAdapter(null);
				}

				PlaceSession.updated = true;

				//togo has placed
			} else if(PlaceSession.type == CONS.PLACESESSION_PLACED_TOGO) {
				String place = getResources().getString(R.string.place);
				String togo = getResources().getString(R.string.togo);

				placeButton.setText(place);
				customerText.setText(togo);
				customerText.setVisibility(View.VISIBLE);

				nullSection.setVisibility(View.GONE);
				orderedSection.setVisibility(View.GONE);
				placedSection.setVisibility(View.VISIBLE);

				if(!PlaceSession.emptyPlaceProducts()) {
					placeNotice.setVisibility(View.INVISIBLE);
					placeProductAdapter = new PlaceProductAdapter(swipeLayout);
					placeProductList.setAdapter(placeProductAdapter);
				} else {
					placeNotice.setVisibility(View.VISIBLE);
					placeProductList.setAdapter(null);
				}

				PlaceSession.updated = true;

				//pickup has placed
			} else if(PlaceSession.type == CONS.PLACESESSION_PLACED_PICKUP) {
				String place = getResources().getString(R.string.place);
				String pickup = getResources().getString(R.string.pickup);

				placeButton.setText(place);
				customerText.setText(pickup);
				customerText.setVisibility(View.VISIBLE);

				nullSection.setVisibility(View.GONE);
				orderedSection.setVisibility(View.GONE);
				placedSection.setVisibility(View.VISIBLE);

				if(!PlaceSession.emptyPlaceProducts()) {
					placeNotice.setVisibility(View.INVISIBLE);
					placeProductAdapter = new PlaceProductAdapter(swipeLayout);
					placeProductList.setAdapter(placeProductAdapter);
				} else {
					placeNotice.setVisibility(View.VISIBLE);
					placeProductList.setAdapter(null);
				}

				PlaceSession.updated = true;

				//order has placed
			} else if (PlaceSession.type == CONS.PLACESESSION_ORDERED) {
				String update = getResources().getString(R.string.update);
				String NOtable = getResources().getString(R.string.NO_table);

				placeButton.setText(update);
				customerText.setText(PlaceSession.customer + NOtable);
				customerText.setVisibility(View.VISIBLE);

				nullSection.setVisibility(View.GONE);
				placedSection.setVisibility(View.GONE);
				orderedSection.setVisibility(View.VISIBLE);

				if(!PlaceSession.emptyPlaceProducts()) {
					placeProductAdapter = new PlaceProductAdapter(swipeLayout);
					placeProductList.setAdapter(placeProductAdapter);
					placeNotice.setVisibility(View.INVISIBLE);
					PlaceSession.updated = true;

				} else {
					ArrayList<OrderProduct> orderProducts = OrderSession.getOrderProducts(PlaceSession.orderId);

					//order find locally
					if(orderProducts.size() > 0) {
						PlaceSession.clearPlaceProducts();

						for(int i = 0; i < orderProducts.size(); i++) {
							OrderProduct orderProduct = orderProducts.get(i);
							int productId = orderProduct.getProductId();
							String name = orderProduct.getName();
							double price = orderProduct.getPrice();
							int quantity = orderProduct.getQuantity();
							String specialRequest = orderProduct.getSpecialRequest();

							PlaceProduct placeProduct = new PlaceProduct();
							placeProduct.setProductId(productId);
							placeProduct.setName(name);
							placeProduct.setPrice(price);
							placeProduct.setQuantity(quantity);
							PlaceSession.updatePlaceProduct(placeProduct);
						}

						placeProductAdapter = new PlaceProductAdapter(swipeLayout);
						placeProductList.setAdapter(placeProductAdapter);
						placeNotice.setVisibility(View.INVISIBLE);
						PlaceSession.updated = true;

						//not find, try server
					} else {
						placeTable.setVisibility(View.INVISIBLE);
						progressbar.setVisibility(View.VISIBLE);

						DisplayTask displayTask = new DisplayTask();
						displayTask.execute();
					}
				}

				//no order
			} else {
				placedSection.setVisibility(View.GONE);
				orderedSection.setVisibility(View.GONE);
				nullSection.setVisibility(View.VISIBLE);
			}
		}
	}

	class PlaceTask extends AsyncTask<Void, Void, Void> {

		private JSONObject outdata;

		@Override
		protected Void doInBackground(Void... params) {
			outdata = PlaceScript.execute();
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			try {
				int httpCode = outdata.getInt("http_code");

				//http success
				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");
					int status = data.getInt("status");

					//place success
					if(status == STATS.PLACE_SUCCESS) {
						//move to tablefragment
						moveToFragment(2);

						//clear place session
						PlaceCounter.clear();
						PlaceSession.clear();

						//display default view
						swipeLayout.close(true);
						placeProductList.setAdapter(null);
						placeTable.setVisibility(View.VISIBLE);
						placeNotice.setVisibility(View.VISIBLE);
						progressbar.setVisibility(View.INVISIBLE);
						customerText.setVisibility(View.INVISIBLE);

						OrderSession.updated = false;

						//place error
					} else {
						String rightBtnText = getResources().getString(R.string.yes);
						String content = getResources().getString(R.string.place_error);

						final PosDialog posDialog = new PosDialog(Glob.context);
						posDialog.setContent(content);
						posDialog.setRightBtnText(rightBtnText);
						posDialog.show();

						Button buttonRight = (Button)posDialog.findViewById(R.id.button_right);

						buttonRight.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								posDialog.dismiss();
								progressbar.setVisibility(View.INVISIBLE);
								placeTable.setVisibility(View.VISIBLE);
							}
						});
					}

					//http error
				} else {
					String rightBtnText = getResources().getString(R.string.yes);
					String content = getResources().getString(R.string.network_error);

					final PosDialog posDialog = new PosDialog(Glob.context);
					posDialog.setContent(content);
					posDialog.setRightBtnText(rightBtnText);
					posDialog.show();

					Button buttonRight = (Button)posDialog.findViewById(R.id.button_right);

					buttonRight.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							posDialog.dismiss();
							progressbar.setVisibility(View.INVISIBLE);
							placeTable.setVisibility(View.VISIBLE);
						}
					});
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}
		}
	}

	class UpdateTask extends AsyncTask<Void, Void, Void> {

		private JSONObject outdata;

		@Override
		protected Void doInBackground(Void... params) {
			outdata = UpdateScript.execute();
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			try {
				int httpCode = outdata.getInt("http_code");

				//http success
				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");
					int status = data.getInt("status");

					if(status == STATS.UPDATE_SUCCESS) {
						//alert
						final SuccessAlertDialog successAlertDialog = new SuccessAlertDialog(getActivity());
						successAlertDialog.show();

						//clear place session
						PlaceCounter.clear();
						PlaceSession.clear();

						//display default view
						swipeLayout.close(true);
						progressbar.setVisibility(View.INVISIBLE);

						OrderSession.updated = false;

					//update error
					} else {
						String buttonText = getActivity().getResources().getString(R.string.yes);
						Drawable drawable = getActivity().getResources().getDrawable(R.drawable.error);

						final AlertDialog alertDialog = new AlertDialog(getActivity());
						alertDialog.setImage(drawable);
						alertDialog.setButtonText(buttonText);
						alertDialog.show();
					}

					//http error
				} else {
					String rightBtnText = getResources().getString(R.string.yes);
					String content = getResources().getString(R.string.network_error);

					final PosDialog posDialog = new PosDialog(Glob.context);
					posDialog.setContent(content);
					posDialog.setRightBtnText(rightBtnText);
					posDialog.show();

					Button buttonRight = (Button)posDialog.findViewById(R.id.button_right);

					buttonRight.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							posDialog.dismiss();
							progressbar.setVisibility(View.INVISIBLE);
							placeTable.setVisibility(View.VISIBLE);
						}
					});
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}
		}
	}

	class CancelTask extends AsyncTask<Void, Void, Void> {

		private JSONObject outdata;
	
		@Override
		protected Void doInBackground(Void... params) {
			outdata = CancelScript.execute();
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {

		}
	}

	class UnsetTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
			String tableIdStr = Integer.toString(PlaceSession.tableId);
			nameValuePairs.add(new BasicNameValuePair("table_id", tableIdStr));
			JSONObject outdata = PostNetData.getResult(ADDR.UNSET, nameValuePairs);

			try {
				int httpCode = outdata.getInt("http_code");

				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");
					int status = data.getInt("status");
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			buttonGroup.setVisibility(View.VISIBLE);
			progressbarPanel.setVisibility(View.INVISIBLE);

			moveToFragment(2);
		}
	}

	class VoidTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
			String orderIdStr = Integer.toString(PlaceSession.orderId);
			nameValuePairs.add(new BasicNameValuePair("order_id", orderIdStr));
			JSONObject outdata = PostNetData.getResult(ADDR.VOID, nameValuePairs);

			try {
				int httpCode = outdata.getInt("http_code");

				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");
					int status = data.getInt("status");
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			buttonGroup.setVisibility(View.VISIBLE);
			progressbarPanel.setVisibility(View.INVISIBLE);

			moveToFragment(2);
		}
	}

	class HoldTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
			String orderIdStr = Integer.toString(PlaceSession.orderId);
			nameValuePairs.add(new BasicNameValuePair("order_id", orderIdStr));
			JSONObject outdata = PostNetData.getResult(ADDR.HOLD, nameValuePairs);

			try {
				int httpCode = outdata.getInt("http_code");

				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");
					int status = data.getInt("status");
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			buttonGroup.setVisibility(View.VISIBLE);
			progressbarPanel.setVisibility(View.INVISIBLE);

			moveToFragment(2);
		}
	}

	class DisplayTask extends AsyncTask<Void, Void, Void> {

		private ArrayList<OrderProduct> orderProducts = new ArrayList<OrderProduct>();

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
			String orderIdStr = Integer.toString(PlaceSession.orderId);
			nameValuePairs.add(new BasicNameValuePair("order_id", orderIdStr));
			JSONObject outdata = PostNetData.getResult(ADDR.ORDER, nameValuePairs);

			try {
				int httpCode = outdata.getInt("http_code");

				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");
					int status = data.getInt("status");

					if(status == 2000) {
						JSONArray orderProductsJson = data.getJSONArray("order_products");

						for(int i = 0; i < orderProductsJson.length(); i++) {
							JSONObject orderProductJson = (JSONObject)orderProductsJson.get(i);
							int productId = orderProductJson.getInt("product_id");
							String name = orderProductJson.getString("name");
							double price = orderProductJson.getDouble("price");
							int quantity = orderProductJson.getInt("quantity");

							OrderProduct orderProduct = new OrderProduct();
							orderProduct.setProductId(productId);
							orderProduct.setName(name);
							orderProduct.setPrice(price);
							orderProduct.setQuantity(quantity);
							orderProduct.setSpecialRequest("");
							orderProducts.add(orderProduct);
						}
					}
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}


			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			placeTable.setVisibility(View.VISIBLE);
			progressbar.setVisibility(View.INVISIBLE);

			//set current place table
			PlaceSession.clearPlaceProducts();

			if(orderProducts.size() > 0) {
				placeNotice.setVisibility(View.INVISIBLE);

				for(int i = 0; i < orderProducts.size(); i++) {
					OrderProduct orderProduct = orderProducts.get(i);

					int productId = orderProduct.getProductId();
					String name = orderProduct.getName();
					double price = orderProduct.getPrice();
					int quantity = orderProduct.getQuantity();
					String specialRequest = orderProduct.getSpecialRequest();

					PlaceProduct placeProduct = new PlaceProduct();
					placeProduct.setProductId(productId);
					placeProduct.setName(name);
					placeProduct.setPrice(price);
					placeProduct.setQuantity(quantity);
					PlaceSession.updatePlaceProduct(placeProduct);
				}

				placeProductAdapter = new PlaceProductAdapter(swipeLayout);
				placeProductList.setAdapter(placeProductAdapter);
			}

			PlaceSession.updated = true;
		}
	}
}








