package com.online.taxi.order.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.zookeeper.WatchedEvent;
 
 
public class MyCuratorListener implements CuratorListener {
    @Override
    public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
        CuratorEventType type = event.getType();
        if(type == CuratorEventType.WATCHED){
            WatchedEvent watchedEvent = event.getWatchedEvent();
            String path = watchedEvent.getPath();
            System.out.println(watchedEvent.getType()+" -- "+ path);
            // 重新设置改节点监听
            if(null != path){
                client.checkExists().watched().forPath(path);
            }

        }
    }
}