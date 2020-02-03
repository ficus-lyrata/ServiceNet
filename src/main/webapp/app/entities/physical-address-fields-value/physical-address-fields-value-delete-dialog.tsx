import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPhysicalAddressFieldsValue } from 'app/shared/model/physical-address-fields-value.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './physical-address-fields-value.reducer';

export interface IPhysicalAddressFieldsValueDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PhysicalAddressFieldsValueDeleteDialog extends React.Component<IPhysicalAddressFieldsValueDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.physicalAddressFieldsValueEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { physicalAddressFieldsValueEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="serviceNetApp.physicalAddressFieldsValue.delete.question">
          <Translate
            contentKey="serviceNetApp.physicalAddressFieldsValue.delete.question"
            interpolate={{ id: physicalAddressFieldsValueEntity.id }}
          >
            Are you sure you want to delete this PhysicalAddressFieldsValue?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-physicalAddressFieldsValue" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ physicalAddressFieldsValue }: IRootState) => ({
  physicalAddressFieldsValueEntity: physicalAddressFieldsValue.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PhysicalAddressFieldsValueDeleteDialog);
