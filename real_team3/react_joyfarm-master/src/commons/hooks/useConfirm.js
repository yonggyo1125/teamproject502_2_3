export default function _useConfirm(message, onConfirm, onCancel) {
  if (window.confirm(message)) {
    if (typeof onConfirm === 'function') {
      onConfirm();
    }
  } else {
    if (typeof onCancel === 'function') {
      onCancel();
    }
  }
}
