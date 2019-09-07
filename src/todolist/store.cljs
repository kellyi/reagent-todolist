(ns todolist.store
  (:require [reagent.core :as r]
            [alandipert.storage-atom :refer [local-storage]]
            [todolist.utils :as utils]))

(def empty-input-state "")

(def stored-app-state
  "Application state persisted to local storage."
  (local-storage
   (r/atom {:todos {:data []
                    :input empty-input-state
                    :show-only-unfinished false}})
   :stored-state))

(def unstored-app-state
  "Ephemeral application state."
  (r/atom {:input empty-input-state
           :show-only-unfinished false}))

(def todo-data-cursor
  "A lens to allow getting and setting todos data directly."
  (r/cursor stored-app-state [:todos :data]))

(def todo-input-cursor
  "A lens to allow getting and setting text input state directly."
  (r/cursor unstored-app-state [:input]))

(def todo-show-only-unfinished-cursor
  "A lens to allow getting and setting a filter to show only unfinished items."
  (r/cursor unstored-app-state [:show-only-unfinished]))

(defn handle-toggle-show-only-unfinished-filter
  "Toggle the filter to display only unfinished items."
  []
  (reset! todo-show-only-unfinished-cursor
          (if @todo-show-only-unfinished-cursor
            false
            true)))

(defn handle-update-todo-input
  "Store the value in the todo text input."
  [e]
  (->> e
      utils/get-value-from-event
      (reset! todo-input-cursor)))

(defn create-new-todo-list-item
  "Create a new todo list item from the todo text input."
  []
  (let [new-todo-item (utils/create-todo-item @todo-input-cursor)]
    (swap! todo-data-cursor conj new-todo-item)
    (reset! todo-input-cursor empty-input-state)))

(defn delete-todo-list-item-at-index
  "Delete the todo list item at the specified index."
  [index]
  (let [updated-todo-list (into []
                                (concat (subvec @todo-data-cursor 0 index)
                                        (subvec @todo-data-cursor (inc index))))]
    (reset! todo-data-cursor updated-todo-list)
    (and (zero? (count updated-todo-list))
         (reset! todo-show-only-unfinished-cursor false))))

(defn toggle-list-item-status-at-index
  "Toggle the `:completed` status of the todo list item at the specified index."
  [index]
  (let [updated-item (->> index
                          (nth @todo-data-cursor)
                          utils/update-todo-item-completed-status)]
    (println updated-item)
    (reset! todo-data-cursor
            (into []
                  (concat (subvec @todo-data-cursor 0 index)
                          [updated-item]
                          (subvec @todo-data-cursor (+ 1 index)))))))

(defn log-state
  "Log changes to the app store atom."
  [logger atom prev next]
  (do
    (.log js/console logger)
    (.log js/console atom)))

(def logger
  "Add a logger to run during development."
  (when js/goog.DEBUG
    (add-watch stored-app-state :stored-app-state log-state)
    (add-watch unstored-app-state :unstored-app-state log-state)))
