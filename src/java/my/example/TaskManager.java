/*
 * The MIT License
 *
 * Copyright 2018 Grzegorz Skorupa <g.skorupa at gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package my.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.cricketmsf.Event;
import org.cricketmsf.Kernel;
import org.cricketmsf.out.OutboundAdapter;
import org.cricketmsf.out.db.KeyValueDBException;
import org.cricketmsf.out.db.KeyValueDBIface;

public class TaskManager extends OutboundAdapter implements TaskManagerIface {

    private KeyValueDBIface database;
    private String tableName = "tasks";

    @Override
    public Task create(Task task) {
        try {
            if (!database.containsKey(tableName, task.id)) {
                database.put(tableName, task.id, task);
                return (Task) database.get(tableName, task.id);
            }else{
                return null;
            }
        } catch (KeyValueDBException ex) {
            Kernel.getInstance().dispatchEvent(Event.logSevere(this, ex.getMessage()));
            return null;
        }
    }

    @Override
    public Task read(String id) {
        try {
            return (Task) database.get(tableName, id);
        } catch (KeyValueDBException ex) {
            Kernel.getInstance().dispatchEvent(Event.logInfo(this, ex.getMessage()));
        }
        return null;
    }

    @Override
    public Task update(Task task) {
        try {
            if (database.containsKey(tableName, task.id)) {
                database.put(tableName, task.id, task);
                return (Task) database.get(tableName, task.id);
            }else{
                return null;
            }
        } catch (KeyValueDBException ex) {
            Kernel.getInstance().dispatchEvent(Event.logSevere(this, ex.getMessage()));
            return null;
        }

    }

    @Override
    public boolean delete(String id) {
        try {
            return database.remove(tableName, id);
        } catch (KeyValueDBException ex) {
            Kernel.getInstance().dispatchEvent(Event.logSevere(this, ex.getMessage()));
        }
        return false;
    }

    @Override
    public List getAll() {
        ArrayList list = new ArrayList();
        try {
            Map map = database.getAll(tableName);
            map.keySet().forEach(
                    key -> list.add(map.get(key))
            );
        } catch (KeyValueDBException ex) {
            Kernel.getInstance().dispatchEvent(Event.logSevere(this, ex.getMessage()));
        }
        return list;
    }

    @Override
    public void init(KeyValueDBIface database) {
        this.database = database;
        try {
            database.addTable("tasks", 0, true);
        } catch (KeyValueDBException ex) {
            Kernel.getInstance().dispatchEvent(Event.logInfo(this, ex.getMessage()));
        }
    }

}
