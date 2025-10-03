/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorPart;

import com.jaspersoft.studio.editor.JrxmlEditor;
import com.jaspersoft.studio.editor.report.AbstractVisualEditor;
import com.jaspersoft.studio.editor.report.ReportContainer;
import com.jaspersoft.studio.utils.SelectionHelper;

public class TFContainer extends Composite {
	private StackLayout stackLayout;
	private ToolBar toolBar;
	private ToolBar additionalToolbar;
	private ToolBarManager additionalToolbarManager;

	public TFContainer(Composite parent, int style) {
		super(parent, style);
		GridLayout layout = new GridLayout(2,false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		setLayout(layout);

		toolBar = new ToolBar(this, SWT.HORIZONTAL | SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false));
		
		// 创建上下文菜单, 添加菜单项
		Menu contextMenu = new Menu(toolBar.getShell(), SWT.POP_UP);
		MenuItem closeAllItem = new MenuItem(contextMenu, SWT.PUSH);
		closeAllItem.setText("close all but Main Report");
		
		// 添加菜单项点击事件
		closeAllItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IEditorPart currentEditor = SelectionHelper.getActiveJRXMLEditor();
				if (currentEditor instanceof JrxmlEditor){
					JrxmlEditor editor = (JrxmlEditor) currentEditor;
					ReportContainer currentContainer =  editor.getReportContainer();
					currentContainer.updateVisualView();
				}
			}
		});
		
		// 创建上下文菜单
		MenuItem closeOneItem = new MenuItem(contextMenu, SWT.PUSH);
		closeOneItem.setText("close this subeditor");
		
		closeOneItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ToolItem selectedItem = (ToolItem) contextMenu.getData("selectedItem");
				if (selectedItem != null) {
					int newPageIndex = indexOf((TFItem) selectedItem.getData());
					
					IEditorPart currentEditor = SelectionHelper.getActiveJRXMLEditor();
					if (currentEditor instanceof JrxmlEditor){
						JrxmlEditor editor = (JrxmlEditor) currentEditor;
						ReportContainer currentContainer =  editor.getReportContainer();
						if (newPageIndex > 0) {
							currentContainer.removeVisualView(newPageIndex);
						}
					}
				}
			}
		});
		
		// 工具栏右键监听，更新选中ToolItem
		toolBar.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.button == 3) {
					ToolItem item = toolBar.getItem(new Point(event.x, event.y));
					if (item != null) {
						contextMenu.setData("selectedItem", item);
					}
				}
			}
		});
		
		// 添加鼠标监听器来检测右键点击
		toolBar.addListener(SWT.MenuDetect, event -> {
			contextMenu.setLocation(event.x, event.y);
			contextMenu.setVisible(true);
		});
		/*toolBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// 检查是否是右键点击
				if (e.button == 3) {
					// 显示上下文菜单
					contextMenu.setVisible(true);
				}
			}
		});*/
		
		additionalToolbar = new ToolBar(this, SWT.HORIZONTAL | SWT.FLAT | SWT.RIGHT);
		GridData additionalToolbarGD = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		additionalToolbar.setLayoutData(additionalToolbarGD);
		additionalToolbarManager = new ToolBarManager(additionalToolbar);
		
		content = new Composite(this, SWT.NONE);
		stackLayout = new StackLayout();
		stackLayout.marginWidth = 0;
		stackLayout.marginHeight = 0;
		content.setLayout(stackLayout);
		content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
	}

	public Composite getContent() {
		return content;
	}

	private List<TFItem> tfitems = new ArrayList<TFItem>();
	private int selection = -1;

	public int indexOf(TFItem item) {
		return tfitems.indexOf(item);
	}

	public int getSelectionIndex() {
		return selection;
	}

	public void setSelection(int selection) {
		this.selection = selection;
		for (int i = 0; i < toolBar.getItemCount(); i++) {
			toolBar.getItem(i).setSelection(i == selection);
		}
		stackLayout.topControl = getItem(selection).getControl();
		
		Object data = toolBar.getItem(selection).getData();
		if(data instanceof TFItem){
			TFItem tfItem=(TFItem)data; 
			if(tfItem.getData() instanceof AbstractVisualEditor){
				populateAdditionalToolbar((AbstractVisualEditor) tfItem.getData());
			}
		}
		
		getParent().layout();
	}

	public void removeItem(TFItem item) {
		int index = tfitems.indexOf(item);
		toolBar.getItem(index).dispose();
		tfitems.remove(item);
		toolBar.update();
		this.pack();
		this.layout(true);
		if (index == selection)
			setSelection(--index);
	}

	public TFItem getItem(int selectedIndex) {
		return tfitems.get(selectedIndex);
	}

	public int getItemCount() {
		return tfitems.size();
	}

	private List<SelectionListener> listeners = new ArrayList<SelectionListener>();
	private Composite content;

	public void addSelectionListener(SelectionListener listener) {
		listeners.add(listener);
	}

	public void createItem(final TFItem item, int index) {
		final ToolItem ti = new ToolItem(toolBar, SWT.RADIO);
		ti.setText("Item1" + item.getText());
		ti.setData(item);
		ti.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				if (ti.getSelection() && tfitems.indexOf(item) != selection)
					for (SelectionListener sl : listeners)
						sl.widgetSelected(e);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		tfitems.add(index, item);
		toolBar.update();
		this.pack();
		this.layout(true);
	}
	
	public void update(TFItem tfItem) {
		for (ToolItem it : toolBar.getItems()) {
			if (it.getData() == tfItem) {	
				it.setText(tfItem.getText());
				it.setImage(tfItem.getImage());
				toolBar.update();
				layout(true);
				break;
			}
		}
	}
	
	/*
	 * Enrich the toolbar manager registered for the additional toolbar on the right.
	 */
	private void populateAdditionalToolbar(AbstractVisualEditor editor){
		additionalToolbarManager.removeAll();
		editor.contributeItemsToEditorTopToolbar(additionalToolbarManager);
		additionalToolbarManager.update(true);
	}
}
