<tasks>
  <h3>{ opts.title }</h3>
  <ul>
    <li each={ item, i in items }>{ item.name }</li>
  </ul>
  <form onsubmit={ submitForm }>
        <input type="text" name="task">
      <button type="submit">Create new task </button>
  </form>
  <style>
    h3 {
      font-size: 14px;
    }
  </style>
  <script>
    var self=this
    self.items = []
    self.on('mount', function(){
      self.getTasks()
    })
    self.getTasks = function () {
      getData('http://localhost:9090/api/task/', null, null, self.fillTaskList, self)
    }
    self.fillTaskList = function (text) {
      self.items = JSON.parse(text)
      self.update()
    }
    self.submitForm = function (e) {
        e.preventDefault()
        var fd = new FormData(e.target)
        e.target.reset()
        sendFormData(fd, 'POST', 'http://localhost:9090/api/task/', null, self.getTasks, self)
    }
  </script>
</tasks>
