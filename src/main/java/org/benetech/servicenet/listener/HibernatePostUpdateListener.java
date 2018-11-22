package org.benetech.servicenet.listener;

import org.benetech.servicenet.domain.Metadata;
import org.benetech.servicenet.domain.enumeration.ActionType;
import org.benetech.servicenet.service.MetadataService;
import org.benetech.servicenet.util.HibernateListenerUtils;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HibernatePostUpdateListener implements PostUpdateEventListener {

    @Autowired
    private MetadataService metadataService;

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        if (HibernateListenerUtils.shouldTrackMetadata(event.getEntity())) {
            persistMetaData(event);
        }
    }

    private void persistMetaData(PostUpdateEvent event) {
        List<Metadata> metadata = new ArrayList<>();
        for (Integer fieldId : getIdsOfChangedFields(event.getOldState(), event.getState())) {
            metadata.add(extractMetadata(event, fieldId));
        }
        metadataService.saveForCurrentUser(metadata);
    }

    private List<Integer> getIdsOfChangedFields(Object[] oldState, Object[] newState) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < oldState.length; i++) {
            if (oldState[i] != null && !oldState[i].equals(newState[i])) {
                result.add(i);
            }
        }
        return result;
    }

    private Metadata extractMetadata(PostUpdateEvent event, Integer fieldId) {
        Metadata result = new Metadata();
        result.setResourceId(event.getId().toString());
        result.setFieldName(event.getPersister().getPropertyNames()[fieldId]);
        result.setPreviousValue(event.getOldState()[fieldId].toString());
        result.setReplacementValue(event.getState()[fieldId].toString());
        result.setLastActionType(ActionType.UPDATE);
        return result;
    }
}
