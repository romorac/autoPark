export interface IFilePdf {
  status?: number;
  message?: string;
  result?: any | null;
}

export class FilePdf implements IFilePdf {
  constructor(public status?: number, public message?: string, public result?: any | null) {}
}
