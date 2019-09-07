(ns todolist.views
  (:require
   [reagent.core :as r]
   [todolist.store :as store]
   [todolist.utils :as utils]))

(defn todo-header
  "Render a header compoent."
  []
  [:h2 "todos"])

(defn todo-filters
  "Render a filters component."
  []
  (let [item-count (count (if @store/todo-show-only-unfinished-cursor
                            (filter utils/item-is-not-completed?
                                    @store/todo-data-cursor)
                            @store/todo-data-cursor))
        item-count-label (if (= 1 item-count)
                           " item"
                           " items")]
    [:div.todo-filters
     [:span (str item-count item-count-label)]
     [:div.visibility-toggle
      [:label.form-switch
       [:input {:type "checkbox"
                :checked @store/todo-show-only-unfinished-cursor
                :on-change store/handle-toggle-show-only-unfinished-filter}]
       [:i.form-icon]]
      "Show only unfinished items."]]))

(defn todo-list-input
  "Render an input for adding todo-list-items."
  []
  [:div.todo-list-input-container
   [:input.input-element
    {:on-change store/handle-update-todo-input
     :value @store/todo-input-cursor
     :placeholder "What needs to be done?"}]
   [:button {:on-click store/create-new-todo-list-item
             :disabled (zero? (count @store/todo-input-cursor))}
    [:i.icon.icon-plus]]])

(defn todo-list-item
  "Render a todo list item."
  [index item]
  (let [should-render-item (or (not @store/todo-show-only-unfinished-cursor)
                               (:completed item))]
    (and should-render-item
      [:li.todo-list-item
       {:key (str (:name item) "-" (:created-at item))}
       [:input {:type "checkbox"
                :on-change (fn
                             []
                             (store/toggle-list-item-status-at-index index))
                :checked (:completed item)}]
       [:span (:name item)]
       [:button {:on-click (fn
                             []
                             (store/delete-todo-list-item-at-index index))}
        [:i.icon.icon-delete]]])))

(defn todo-list
  "Render a list of todo-list-items."
  []
  [:ul
   (doall
    (map-indexed todo-list-item @store/todo-data-cursor))])

(defn todo-list-container
  "Render a container for the todo list."
  []
  [:div.todo-list-container
   (todo-list-input)
   (and (-> @store/todo-data-cursor count zero? not)
        (todo-filters))
   (todo-list)])
