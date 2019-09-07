(ns todolist.core
    (:require
     [reagent.core :as r]
     [todolist.views :as views]))

(defn home-page
  "Render the application's home page."
  []
  [:div.home-page
   (views/todo-header)
   (views/todo-list-container)])

(defn mount-root
  "Render the application to a specified DOM element."
  []
  (r/render [home-page]
            (.getElementById js/document "app")))

(defn init!
  "Start the application."
  []
  (mount-root))
