(ns todolist.utils)

(defn create-todo-item
  "From a name input, create a new todo list item."
  [name]
  {:name name
   :completed false
   :created-at (.getTime (js/Date.))})

(defn update-todo-item-completed-status
  "Return a new todo list item with a different completed status."
  [item]
  {:name (:name item)
   :created-at (:created-at item)
   :completed (if (:completed item)
                false
                true)})

(defn item-is-not-completed?
  "Is an item not completed?"
  [item]
  (-> item :completed not))

(defn get-value-from-event
  "Get the value of a DOM event, like an input change."
  [e]
  (.. e -target -value))
