/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package eclipse.plugin.aiassistant.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "eclipse.plugin.aiassistant.messages.messages"; //$NON-NLS-1$
	public static String NewConversation;
	
	public static String PreviousTab;
	public static String NextTab;
	public static String NewTab;
	public static String CloneCurrentTab;
	public static String CloseAllTabs;
	
	public static String Discuss;
	public static String DiscussTooltip;
	public static String Explain;
	public static String ExplainTooltip;
	public static String CodeReview;
	public static String CodeReviewTooltip;
	public static String BestPractices;
	public static String BestPracticesTooltip;
	public static String Robustify;
	public static String RobustifyTooltip;
	public static String Optimize;
	public static String OptimizeTooltip;
	public static String Debug;
	public static String DebugTooltip;
	public static String Refactor;
	public static String RefactorTooltip;
	public static String WriteComments;
	public static String WriteCommentsTooltip;
	
	public static String ARROW_UP_TOOLTIP;
	public static String ARROW_DOWN_TOOLTIP;
	public static String CLEAR_MESSAGES_TOOLTIP;
	public static String SETTINGS_TOOLTIP;
	public static String INPUT_AREA_TOOLTIP;
	
	public static String UNDO_NAME;
	public static String UNDO_TOOLTIP;
	public static String REDO_NAME;
	public static String REDO_TOOLTIP;
	public static String CLEAR_NAME;
	public static String CLEAR_TOOLTIP;
	public static String START_NAME;
	public static String START_TOOLTIP;
	public static String STOP_NAME;
	public static String STOP_TOOLTIP;
	
	public static String Undo;
	public static String Redo;
	public static String Cut;
	public static String Copy;
	public static String Paste;
	public static String SelectAll;
	
	public static String GENERAL_SETTINGS;
	public static String CURRENT_API_SETTINGS;
	public static String BOOKMARKED_API_SETTINGS;
	
	public static String ConnectionTimeouts;
	public static String RequestTimeouts;
	public static String ChatFontSize;
	public static String NotificationFontSize;
	public static String UserInputFontSize;
	public static String StreamingIntervalms;
	public static String DisableTooltips;
	
	public static String NickName;
	public static String ModelName;
	public static String APIURL;
	public static String APIKey;
	public static String JSONOverrides;
	public static String JSONHeaderOverrides;
	public static String UseStreaming;
	public static String SystemMessage;
	public static String DeveloperMessage;
	
	public static String TableColumnNickName;
	public static String TableColumnModelName;
	public static String TableColumnAPIURL;
	public static String TableColumnJSONOverrides;
	public static String TableColumnJSONHeaderOverrides;
	public static String TableColumnStreaming;
	public static String TableColumnSystem;
	public static String TableColumnDeveloper;
	
	public static String Bookmark;
	public static String BookmarkTooltip;
	public static String Unbookmark;
	public static String UnbookmarkTooltip;
	public static String Validate;
	public static String ValidateTooltip;
	
	public static String Clear;
	public static String ClearTooltip;
	public static String Sort;
	public static String SortTooltip;
	public static String Populate;
	public static String PopulateTooltip;
	
	public static String Hide;
	public static String Show;
	public static String Showorhidepassword;
	
	public static String Vieworedittheprompttemplates;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
